package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

@Composable
actual fun PlatformColors(
    statusBarColor: Color,
    bottomEdgeColor: Color
){}