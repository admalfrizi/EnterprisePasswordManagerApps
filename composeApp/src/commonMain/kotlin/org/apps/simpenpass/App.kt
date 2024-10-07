package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.apps.simpenpass.di.ktorModules
import org.apps.simpenpass.di.remoteDataModule
import org.apps.simpenpass.di.repoModule
import org.apps.simpenpass.di.viewModelModule
import org.apps.simpenpass.presentation.ui.RootScreen
import org.apps.simpenpass.style.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    Napier.base(DebugAntilog())
    KoinApplication(
        application = {
            modules(
                ktorModules, repoModule, remoteDataModule, viewModelModule
            )
        },
    ){
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
}