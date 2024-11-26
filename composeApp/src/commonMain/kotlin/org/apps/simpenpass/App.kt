package org.apps.simpenpass

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
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
import coil3.compose.setSingletonImageLoaderFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.presentation.ui.add_group.AddGroupScreen
import org.apps.simpenpass.presentation.ui.change_data_screen.ChangeBiodataScreen
import org.apps.simpenpass.presentation.ui.change_data_screen.ChangePassScreen
import org.apps.simpenpass.presentation.ui.change_data_screen.OtpScreen
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormScreen
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataPassUser
import org.apps.simpenpass.presentation.ui.main.SplashViewModel
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.screen.authNavGraph
import org.apps.simpenpass.screen.groupPassDetail
import org.apps.simpenpass.style.AppTheme
import org.apps.simpenpass.utils.getAsyncImageLoader
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
            setSingletonImageLoaderFactory { context ->
                getAsyncImageLoader(context)
            }

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
                },
                navigateToOtpFirst = {
                    navController.navigate(Screen.Otp.dataType(it))
                }
            )
        }

        groupPassDetail(navController, density,bottomEdgeColor)

        composable(
            route = Screen.AddGroupPass.route
        ){
            AddGroupScreen(
                bottomEdgeColor = bottomEdgeColor,
                navController
            )
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
                bottomEdgeColor ,
                navigateToFormEdit = {
                    navController.navigate(Screen.FormPassData.passDataId(it))
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.Otp.route,
            enterTransition = {
                slideInHorizontally { initialOffset ->
                    initialOffset
                }
            },
            exitTransition = {
                slideOutHorizontally { initialOffset ->
                    initialOffset
                }
            },
            arguments = listOf(
                navArgument(Screen.Otp.ARG_DATA_TYPE){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ){
            val dataType = requireNotNull(it.arguments?.getString("dataType"))
            OtpScreen(
                navToBack = {
                    navController.navigateUp()
                },
                navToChangePass = {
                    navController.navigate(Screen.ChangePass.token(it)){
                        popUpTo(Screen.Otp.route){
                            inclusive = true
                        }
                    }
                },
                navToChangeBiodata = {
                    navController.navigate(Screen.ChangeBioData.route){
                        popUpTo(Screen.Otp.route){
                            inclusive = true
                        }
                    }
                },
                dataType
            )
        }

        composable(
            route = Screen.ChangePass.route,
            enterTransition = {
                slideInHorizontally { initialOffset ->
                    initialOffset
                }
            },
            exitTransition = {
                slideOutHorizontally { initialOffset ->
                    initialOffset
                }
            },
            arguments = listOf(
                navArgument(Screen.ChangePass.ARG_TOKEN){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ){
            val token = requireNotNull(it.arguments?.getString("token"))
            ChangePassScreen(
                token,
                navToBack = {
                    navController.navigateUp()
                },
                navToHome = {
                    navController.navigate(Screen.Root.route){
                        popUpTo(Screen.ChangePass.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.ChangeBioData.route,
            enterTransition = {
                slideInHorizontally { initialOffset ->
                    initialOffset
                }
            },
            exitTransition = {
                slideOutHorizontally { initialOffset ->
                    initialOffset
                }
            },
//            arguments = listOf(
//                navArgument(Screen.ChangePass.ARG_TOKEN){
//                    type = NavType.StringType
//                    nullable = true
//                    defaultValue = ""
//                }
//            )
        ){
//            val token = requireNotNull(it.arguments?.getString("token"))
            ChangeBiodataScreen(
                navToBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

