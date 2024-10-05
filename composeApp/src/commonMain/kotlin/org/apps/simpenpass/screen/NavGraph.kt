package org.apps.simpenpass.screen

import androidx.compose.animation.AnimatedContentTransitionScope
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.ktor.client.HttpClient
import org.apps.simpenpass.PlatformColors
import org.apps.simpenpass.presentation.ui.add_group.AddGroupScreen
import org.apps.simpenpass.presentation.ui.auth.AuthScreen
import org.apps.simpenpass.presentation.ui.auth.RecoveryPassScreen
import org.apps.simpenpass.presentation.ui.auth.RegisterScreen
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormScreen
import org.apps.simpenpass.presentation.ui.create_role_screen.EditRoleScreen
import org.apps.simpenpass.presentation.ui.group_pass.GroupPassDetail
import org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group.EditAnggotaGroup
import org.apps.simpenpass.presentation.ui.group_pass.retrieve_data_pass.RetrieveDataPass
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataPassUser
import org.apps.simpenpass.presentation.ui.main.group.GroupScreen
import org.apps.simpenpass.presentation.ui.main.home.HomeScreen
import org.apps.simpenpass.presentation.ui.main.profile.ProfileScreen
import org.apps.simpenpass.utils.detectRoute

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues? = null,
    sheetState: ModalBottomSheetState,
){
    val isLogged = true
    val density = LocalDensity.current

    val parentRoute = navController.currentDestination?.parent?.route

    PlatformColors(Color(0xFF052E58))

    NavHost(navController,startDestination = if(isLogged) Screen.Main.route else Screen.Auth.route, modifier = Modifier.fillMaxSize().padding(
        paddingValues ?: PaddingValues()
    )){
            authNavGraph(navController)
            navigation(
                route = Screen.Main.route,startDestination = Screen.Home.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ){
                composable(route = Screen.Home.route,enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                        slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                            with(density) { -30.dp.roundToPx() }
                        } },
                    exitTransition = {   fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                                with(density) {
                                    if(detectRoute(navController) == Screen.Group.route){
                                        (-30).dp.roundToPx()
                                    } else {
                                        (-30).dp.roundToPx()
                                    }
                                }
                            } }
                ){
                    HomeScreen(navController, sheetState)
                }

                composable(route =  Screen.Group.route,
                    enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                            slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                                with(density) {
                                    if(detectRoute(navController) == Screen.Home.route) {
                                        (30).dp.roundToPx()
                                    } else if(detectRoute(navController) == Screen.Profile.route){
                                        -30.dp.roundToPx()
                                    } else {
                                        30.dp.roundToPx()
                                    }
                                }
                            } },
                    exitTransition = {   fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                        slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                            with(density) {
                                if (detectRoute(navController) == Screen.Home.route) {
                                    (-30).dp.roundToPx()
                                } else if(detectRoute(navController) == Screen.Profile.route){
                                    (30).dp.roundToPx()
                                } else {
                                    (-30).dp.roundToPx()
                                }
                            }
                        } }) {
                    GroupScreen(navController)
                }

                composable(route = Screen.Profile.route,
                    enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                        slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                            with(density) { 30.dp.roundToPx() }
                        } },


                    exitTransition = {   fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                                with(density) {
                                    (30).dp.roundToPx()
                                }
                            } }
                    ){
                    ProfileScreen(navController)
                }
            }
            composable(route = Screen.PassData.route,enterTransition = { return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(700)
            ) },
                exitTransition = {slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(700))}){
                FormScreen(navController)
            }
            composable(route = Screen.ListPassDataUser.route, enterTransition = {fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                    slideInVertically(animationSpec = tween(durationMillis = 300)) {
                        with(density) { 30.dp.roundToPx() }
                    }}, exitTransition = {fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                    slideOutVertically(animationSpec = tween(durationMillis = 300)) {
                        with(density) { (30).dp.roundToPx() }
                    }}){
                ListDataPassUser(navController)
            }

            composable(
                route = Screen.GroupPass.route,
                enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                        slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                            with(density) { 30.dp.roundToPx() }
                        } },
                exitTransition = {  fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                        slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                            with(density) { (-30).dp.roundToPx() }
                        }
                }
            ) {
                GroupPassDetail(navController)
            }

            composable(route = Screen.EditAnggota.route,enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                    slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                        with(density) { 30.dp.roundToPx() }
                    } },exitTransition = {  fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                    slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                        with(density) { (-30).dp.roundToPx() }
                    }
            }) {
                EditAnggotaGroup(navController)
            }

            composable(route = Screen.RetrieveDataPass.route,enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                    slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                        with(density) { 30.dp.roundToPx() }
                    } },exitTransition = {  fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                    slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                        with(density) { (-30).dp.roundToPx() }
                    }
            }) {
                RetrieveDataPass(navController)
            }

            composable(route = Screen.AddGroupPass.route){
                AddGroupScreen(navController)
            }

            composable(route = Screen.EditRole.route){
                EditRoleScreen(navController)
            }
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(route = Screen.Auth.route,startDestination = Screen.Login.route){
        composable(route = Screen.Login.route){
            AuthScreen(navController)
        }

        composable(route = Screen.Register.route){
            RegisterScreen(navController)
        }

        composable(route = Screen.RecoveryPass.route){
            RecoveryPassScreen(navController)
        }
    }
}