package org.apps.simpenpass

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.presentation.ui.add_group.AddGroupScreen
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormScreen
import org.apps.simpenpass.presentation.ui.create_role_screen.EditRoleScreen
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataPassUser
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.screen.authNavGraph
import org.apps.simpenpass.screen.groupPassDetail
import org.apps.simpenpass.style.AppTheme
import org.apps.simpenpass.style.primaryColor
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val localStoreData : LocalStoreData = koinInject()
    var startScreen by remember { mutableStateOf(Screen.Auth.route) }
    Napier.base(DebugAntilog())

    val checkNav = navController.currentBackStackEntry?.destination?.parent?.route
    var bottomEdgeColor by remember { mutableStateOf(Color.White) }

    if (checkNav == Screen.Main.route) {
        bottomEdgeColor = primaryColor
    } else if(checkNav == Screen.Auth.route || navController.currentBackStackEntry?.destination?.route == Screen.FormPassData.route){
        bottomEdgeColor = secondaryColor
    } else {
        bottomEdgeColor = Color(0xFFF1F1F1)
    }

    PlatformColors(Color(0xFF052E58),bottomEdgeColor)

    LaunchedEffect(Unit){
        localStoreData.checkLoggedIn().flowOn(Dispatchers.Default).collect { isLoggedIn ->
            startScreen = if(isLoggedIn){
                Screen.Root.route
            } else {
                Screen.Auth.route
            }
        }
    }

    val density = LocalDensity.current

    AppTheme(
        content = {
            NavHost(navController = navController, startDestination = startScreen) {
                authNavGraph(
                    navController = navController
                )
                composable(route = Screen.Root.route){
                    RootScreen(
                        navigateToLogout = {
                            navController.navigate(Screen.Auth.route){
                                popUpTo(Screen.Root.route){
                                    inclusive = false
                                }
                            }
                        },
                        navigateToAddGroup = {
                            navController.navigate(Screen.AddGroupPass.route)
                        },
                        navigateToGroupDtl = {
                            navController.navigate(Screen.GroupPass.groupId(it))
                        },
                        navigateToListUserPass = {
                            navController.navigate(Screen.ListPassDataUser.route)
                        },
                        navigateToEditPass = {
                            navController.navigate(Screen.FormPassData.passDataId(it))
                        },
                        navigateToFormPass = {
                            navController.navigate(Screen.FormPassData.route)
                        }
                    )
                }

                groupPassDetail(navController, density)

                composable(route = Screen.AddGroupPass.route){
                    AddGroupScreen(navController)
                }

                composable(route = Screen.EditRole.route){
                    EditRoleScreen(navController)
                }

                composable(route = Screen.FormPassData.route,
                    enterTransition = { return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(700)
                ) },
                    exitTransition = {slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(700))},
                    arguments = listOf(
                        navArgument(Screen.FormPassData.ARG_PASS_ID){
                            type = NavType.StringType
                            nullable = true
                            defaultValue = ""
                        }
                    )
                ){
                    val passId = requireNotNull(it.arguments?.getString(Screen.FormPassData.ARG_PASS_ID))
                    FormScreen(navController, passId = passId)
                }

                composable(
                    route = Screen.ListPassDataUser.route,
                    enterTransition = {
                        fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                        slideInVertically(animationSpec = tween(durationMillis = 300)) {
                            with(density) { 30.dp.roundToPx() }
                        }
                    } ,
                    exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                        slideOutVertically(animationSpec = tween(durationMillis = 300)) {
                            with(density) { (30).dp.roundToPx() }
                        }
                }){
                    ListDataPassUser(
                        navigateToFormEdit = {
                            navController.navigate(Screen.FormPassData.passDataId(it))
                        },
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    )
}