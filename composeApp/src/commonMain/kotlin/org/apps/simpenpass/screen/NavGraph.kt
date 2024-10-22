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
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.presentation.ui.add_group.AddGroupScreen
import org.apps.simpenpass.presentation.ui.auth.AuthScreen
import org.apps.simpenpass.presentation.ui.auth.RecoveryPassScreen
import org.apps.simpenpass.presentation.ui.auth.RegisterScreen
import org.apps.simpenpass.presentation.ui.auth.SendOtpScreen
import org.apps.simpenpass.presentation.ui.auth.VerifyOtpScreen
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

@Composable
fun ContentNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues? = null,
    sheetState: ModalBottomSheetState,
    data: MutableState<PassResponseData?>,
    navigateToFormWithArgs : MutableState<(PassResponseData)->Unit>,
    navigateToLogout: () -> Unit
) {
    val density = LocalDensity.current

    NavHost(navController,startDestination = Screen.Main.route , modifier = Modifier.fillMaxSize().padding(
        paddingValues ?: PaddingValues()
    )){
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
                    HomeScreen(
                        navController,
                        sheetState,
                        data,
                        passDataId = navigateToFormWithArgs,
                        navigateToFormEdit = {
                            navController.navigate(Screen.FormPassData.passDataId(it))
                        }
                    )
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
                    GroupScreen(navigateToGrupDtl = {
                        navController.navigate(Screen.GroupPass.groupId(it))
                    },sheetState = sheetState)
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
                    ProfileScreen(navController, navigateToLogout = navigateToLogout)
                }
            }
            composable(route = Screen.FormPassData.route,enterTransition = { return@composable slideIntoContainer(
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
            composable(route = Screen.ListPassDataUser.route, enterTransition = {fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                    slideInVertically(animationSpec = tween(durationMillis = 300)) {
                        with(density) { 30.dp.roundToPx() }
                    }}, exitTransition = {fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                    slideOutVertically(animationSpec = tween(durationMillis = 300)) {
                        with(density) { (30).dp.roundToPx() }
                    }}){
                ListDataPassUser(
                    navigateToFormEdit = {
                        navController.navigate(Screen.FormPassData.passDataId(it))
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            groupPassDetail(navController,density)

            composable(route = Screen.AddGroupPass.route){
                AddGroupScreen(navController)
            }

            composable(route = Screen.EditRole.route){
                EditRoleScreen(navController)
            }
    }
}

fun NavGraphBuilder.groupPassDetail(
    navController: NavHostController,
    density: Density
){
    navigation(
        startDestination = Screen.GroupPassDtl.route,
        route = Screen.GroupPass.route,
        arguments = listOf(
            navArgument(Screen.GroupPass.ARG_GROUP_ID){
                type = NavType.StringType
                nullable = true
                defaultValue = ""
            }
        )
    ){
        composable(
            route = Screen.GroupPassDtl.route,
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
            val groupId = requireNotNull(it.arguments?.getString(Screen.GroupPass.ARG_GROUP_ID))

            GroupPassDetail(
                navController,
                groupId = groupId
            )
        }

        composable(
            route = Screen.EditAnggota.route,
            enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
                    slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                        with(density) { 30.dp.roundToPx() }
                    } },
            exitTransition = {  fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
                    slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                        with(density) { (-30).dp.roundToPx() }
                    }
            },
            arguments = listOf(
                navArgument(Screen.EditAnggota.ARG_GROUP_ID){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ) {
            val groupId = it.arguments?.getString(Screen.EditAnggota.ARG_GROUP_ID)

            EditAnggotaGroup(navController,groupId = groupId!!)
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

        composable(route = Screen.SendOtp.route){
            SendOtpScreen(
                navBack = {
                    navController.navigateUp()
                },
                navToVerifyOtp = { userId ->
                    navController.navigate(Screen.VerifyOtp.userId(userId))
                }
            )
        }

        composable(route = Screen.VerifyOtp.route,
            arguments = listOf(
                navArgument(Screen.VerifyOtp.ARG_USER_ID){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ){
            val userId = it.arguments?.getString(Screen.VerifyOtp.ARG_USER_ID)

            VerifyOtpScreen(
                navBack = {
                    navController.navigateUp()
                },
                navToResetPass = { token ->
                    navController.navigate(Screen.RecoveryPass.token(token)){
                        popUpTo(Screen.VerifyOtp.route){
                            inclusive = true
                        }
                    }
                },
                userId = userId!!
            )
        }

        composable(route = Screen.RecoveryPass.route,
            arguments = listOf(
                navArgument(Screen.RecoveryPass.ARG_TOKEN){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ){
            val token = it.arguments?.getString(Screen.RecoveryPass.ARG_TOKEN)

            RecoveryPassScreen(
                token = token!!,
                navBack = {
                    navController.navigateUp()
                },
                navToLogin = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.RecoveryPass.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

