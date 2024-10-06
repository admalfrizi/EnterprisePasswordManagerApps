package org.apps.simpenpass

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ChangeStatusBarColor(color = Color(0xFF003376))
            App(
            )
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