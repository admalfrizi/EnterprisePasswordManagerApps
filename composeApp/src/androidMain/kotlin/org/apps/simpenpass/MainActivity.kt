package org.apps.simpenpass

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.allViews
import androidx.core.view.updatePadding
import org.apps.simpenpass.style.primaryColor
import org.apps.simpenpass.style.secondaryColor

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ChangeStatusBarColor(color = Color(0xFF003376))
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
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