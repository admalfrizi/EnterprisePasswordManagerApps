package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.presentation.ui.auth.AuthViewModel
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.screen.authNavGraph
import org.apps.simpenpass.style.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val authViewModel : AuthViewModel = koinViewModel()
    var isLoggedIn by remember { mutableStateOf(false) }
    val stateAuth by authViewModel.authState.collectAsState()
    val density = LocalDensity.current

    PlatformColors(Color(0xFF052E58))

    Napier.base(DebugAntilog())

    if(stateAuth.token != null){
        isLoggedIn = true
    }

    AppTheme(
        navController = navController,
        content = {
            NavHost(
                startDestination = if(isLoggedIn) "root" else Screen.Auth.route,
                navController = navController,
            ){
                authNavGraph(navController)
                composable(route = "root"){
                    RootScreen(density)
                }
            }
        }
    )
}