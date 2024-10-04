package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
) {
    val navController = rememberNavController()

    AppTheme(
        navController = navController,
        content = {
            NavHost(
                startDestination = "root",
                navController = navController,
            ){
                composable(route = "root"){
                    RootScreen()
                }
            }
        }
    )
}