package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.screen.authNavGraph
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
                "root"
            } else {
                Screen.Auth.route
            }
        }
    }

    AppTheme(
        content = {
            NavHost(navController = navController, startDestination = startScreen) {
                authNavGraph(
                    navController = navController
                )
                composable(route = "root"){
                    RootScreen(
                        navigateToLogout = {
                            navController.navigate(Screen.Auth.route){
                                popUpTo("root"){
                                    inclusive = false
                                }
                            }
                        }
                    )
                }
            }
        }
    )
}