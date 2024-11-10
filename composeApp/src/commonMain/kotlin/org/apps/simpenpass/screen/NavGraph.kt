package org.apps.simpenpass.screen

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.apps.simpenpass.models.pass_data.DataPass
import org.apps.simpenpass.presentation.ui.auth.AuthScreen
import org.apps.simpenpass.presentation.ui.auth.RecoveryPassScreen
import org.apps.simpenpass.presentation.ui.auth.RegisterScreen
import org.apps.simpenpass.presentation.ui.auth.SendOtpScreen
import org.apps.simpenpass.presentation.ui.auth.VerifyOtpScreen
import org.apps.simpenpass.presentation.ui.group_pass.GroupPassDetail
import org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group.EditAnggotaGroup
import org.apps.simpenpass.presentation.ui.group_pass.retrieve_data_pass.RetrieveDataPass
import org.apps.simpenpass.presentation.ui.main.group.GroupScreen
import org.apps.simpenpass.presentation.ui.main.home.HomeScreen
import org.apps.simpenpass.presentation.ui.main.profile.ProfileScreen
import org.apps.simpenpass.utils.detectRoute

@Composable
fun RootNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues? = null,
    sheetState: ModalBottomSheetState,
    data: MutableState<DataPass?>,
    navigateToFormWithArgs : MutableState<(DataPass)->Unit>,
    navigateToLogout: () -> Unit,
    navigateToGroupDtl: (String) -> Unit,
    navigateToListUserPass : () -> Unit,
    navigateToEditPass: (String) -> Unit,
    navigateToFormPass: () -> Unit
) {
    val density = LocalDensity.current

    NavHost(navController,startDestination = Screen.Home.route , modifier = Modifier.fillMaxSize().padding(
        paddingValues ?: PaddingValues()
    )){
        composable(route = Screen.Home.route,
            enterTransition = {   fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
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
                    navigateToEditPass(it)
                },
                navigateToListUserPass = navigateToListUserPass,
                navigateToForm = navigateToFormPass
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
            GroupScreen(
                navigateToGrupDtl = { navigateToGroupDtl(it) },
                sheetState = sheetState,
            )
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
}

fun NavGraphBuilder.groupPassDetail(
    navController: NavHostController,
    density: Density,
    bottomEdgeColor: MutableState<Color>
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
                groupId = groupId,
                bottomEdgeColor = bottomEdgeColor
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

fun NavGraphBuilder.authNavGraph(
    bottomEdgeColor: MutableState<Color>,
    navController: NavHostController
) {
    navigation(
        route = Screen.Auth.route,
        startDestination = Screen.Login.route
    ){
        composable(route = Screen.Login.route){
            AuthScreen(navController,bottomEdgeColor = bottomEdgeColor)
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

