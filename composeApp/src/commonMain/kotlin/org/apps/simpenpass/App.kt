package org.apps.simpenpass

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.presentation.ui.add_group.AddGroupScreen
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormScreen
import org.apps.simpenpass.presentation.ui.create_role_screen.EditRoleScreen
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataPassUser
import org.apps.simpenpass.presentation.ui.main.SplashViewModel
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.screen.authNavGraph
import org.apps.simpenpass.screen.groupPassDetail
import org.apps.simpenpass.style.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val splashViewModel : SplashViewModel = koinInject()
    val screenState by splashViewModel.currentScreen.collectAsState()
    val density = LocalDensity.current
    val bottomEdgeColor = remember { mutableStateOf(Color.White) }

    Napier.base(DebugAntilog())

    AppTheme(
        bottomEdgeColor.value,
        content = {
            if(screenState != null){
                MainNavigation(
                    bottomEdgeColor,
                    navController,
                    density,
                    screenState!!
                )
            }
        }
    )
}

@Composable
fun MainNavigation(
    bottomEdgeColor: MutableState<Color>,
    navController: NavHostController,
    density: Density,
    screenState: Screen
){
    NavHost(navController = navController, startDestination = screenState.route) {
        authNavGraph(
            bottomEdgeColor = bottomEdgeColor,
            navController = navController
        )
        composable(
            route = Screen.Root.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ){
            RootScreen(
                bottomEdgeColor,
                navigateToLogout = {
                    navController.navigate(Screen.Auth.route){
                        popUpTo(Screen.Root.route){
                            inclusive = true
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

        composable(
            route = Screen.AddGroupPass.route
        ){
            AddGroupScreen(
                bottomEdgeColor = bottomEdgeColor,
                navController
            )
        }

        composable(
            route = Screen.EditRole.route
        ){
            EditRoleScreen(navController)
        }

        composable(
            route = Screen.FormPassData.route,
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
            },
//            enterTransition = { return@composable slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Up,
//                tween(700)
//            ) },
//            exitTransition = {slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(700))},
            arguments = listOf(
                navArgument(Screen.FormPassData.ARG_PASS_ID){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ){
            val passId = requireNotNull(it.arguments?.getString(Screen.FormPassData.ARG_PASS_ID))
            FormScreen(
                bottomEdgeColor,
                navController,
                passId = passId
            )
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
            }
        ){
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