package org.apps.simpenpass

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dev.theolm.rinku.compose.ext.Rinku
import org.apps.simpenpass.presentation.ui.main.SplashViewModel
import org.apps.simpenpass.utils.initializeAppContext
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val splashViewModel : SplashViewModel by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition(
            condition = {
                splashViewModel.isLoading.value
            }
        )
        setContent {
            Rinku {
                val context = LocalContext.current
                initializeAppContext(context)
                App()
            }
        }
    }
}



//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun ChangeStatusBarColor(color: Color, darkIcons: Boolean = false) {
//    val context = LocalContext.current
//
//    // Ensure the context is of type Activity, as required for Window management
//    if (context is Activity) {
//        val window = context.window
//        val decorView: View = window.decorView
//
//        // Change the status bar color
//        window.statusBarColor = color.toArgb()
//
//        // Set whether the status bar icons are dark or light
//        WindowInsetsControllerCompat(window, decorView).isAppearanceLightStatusBars = darkIcons
//    }
//}