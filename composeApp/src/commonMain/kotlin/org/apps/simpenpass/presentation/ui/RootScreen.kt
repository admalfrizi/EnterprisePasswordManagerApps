package org.apps.simpenpass.presentation.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.apps.simpenpass.presentation.components.BottomNavigationBar
import org.apps.simpenpass.presentation.ui.auth.AuthScreen
import org.apps.simpenpass.presentation.ui.auth.RecoveryPassScreen
import org.apps.simpenpass.presentation.ui.auth.RegisterScreen
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormScreen
import org.apps.simpenpass.presentation.ui.group_pass.GroupPassDetail
import org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group.EditAnggotaGroup
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataPassUser
import org.apps.simpenpass.presentation.ui.main.group.GroupScreen
import org.apps.simpenpass.presentation.ui.main.home.HomeScreen
import org.apps.simpenpass.presentation.ui.main.profile.ProfileScreen
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.ContentNavGraph
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.screen.authNavGraph

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val routeNav = listOf(
        BottomNavMenuData.Home,
        BottomNavMenuData.Group,
        BottomNavMenuData.Profile
    )
    val visible by remember {
        mutableStateOf(true)
    }

    val shouldShowBottomBar = navController.currentBackStackEntryAsState().value?.destination?.route in routeNav.map { it.route }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        bottomBar = {
            if(shouldShowBottomBar){
                AnimatedVisibility(visible){
                    BottomNavigationBar(navController, routeNav)
                }

            }


        }
    ) { paddingValues ->
        ContentNavGraph(navController, if(!shouldShowBottomBar) null else paddingValues)
    }
}
