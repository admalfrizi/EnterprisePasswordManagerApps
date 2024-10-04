package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.zeroValue
import platform.CoreGraphics.CGRect
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIDevice
import platform.UIKit.UIView
import platform.UIKit.UINavigationBar
import platform.UIKit.UINavigationBarAppearance
import platform.UIKit.UIStatusBarStyle
import platform.UIKit.UIWindow
import platform.UIKit.UIStatusBarStyleDefault
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.setStatusBarStyle
import platform.UIKit.statusBarManager

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


@Composable
actual fun PlatformColors(
    statusBarColor: Color
) {
    val currentWindow = UIApplication.sharedApplication.keyWindow
    val color = Color.Transparent
    currentWindow?.rootViewController?.view?.backgroundColor = toUIColor(statusBarColor)

    UIApplication.sharedApplication.setStatusBarStyle(
        if (statusBarColor.luminance() > 0.5) UIStatusBarStyleDefault
        else UIStatusBarStyleDarkContent
    )
}

private fun toUIColor(color: Color): UIColor = UIColor(
    red = color.red.toDouble(),
    green = color.green.toDouble(),
    blue = color.blue.toDouble(),
    alpha = color.alpha.toDouble()
)