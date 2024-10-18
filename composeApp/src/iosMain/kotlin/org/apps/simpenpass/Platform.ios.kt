package org.apps.simpenpass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIView
import platform.UIKit.UIWindow

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun statusBarView() = remember {
    val keyWindow: UIWindow? =
        UIApplication.sharedApplication.windows.firstOrNull { (it as? UIWindow)?.isKeyWindow() == true } as? UIWindow
    val safeAreaInsets = UIApplication.sharedApplication.keyWindow?.safeAreaInsets
    val width = UIScreen.mainScreen.bounds.useContents { this.size.width }
    var topInsets = 0.0

    safeAreaInsets?.let {
        topInsets = safeAreaInsets.useContents {
            this.top
        }
    }
    val tag = 3848245L // https://stackoverflow.com/questions/56651245/how-to-change-the-status-bar-background-color-and-text-color-on-ios-13

    val statusBarView = UIView(frame = CGRectMake(0.0,0.0, width, topInsets))

    keyWindow?.viewWithTag(tag) ?: run {
        statusBarView.tag = tag
        statusBarView.layer.zPosition = 999999.0
        keyWindow?.addSubview(statusBarView)
        statusBarView
    }
}

@Composable
actual fun PlatformColors(
    statusBarColor: Color,
    bottomEdgeColor: Color
) {
    val statusBar = statusBarView()

    SideEffect {
        statusBar.backgroundColor = toUIColor(statusBarColor)
    }
    val currentWindow = UIApplication.sharedApplication.keyWindow
    currentWindow?.rootViewController?.view?.backgroundColor = toUIColor(bottomEdgeColor)

//    UIApplication.sharedApplication.setStatusBarStyle(
//        if (statusBarColor.luminance() > 0.5) UIStatusBarStyleDefault
//        else UIStatusBarStyleDarkContent
//    )
//
//    UITabBar.appearance().backgroundColor = toUIColor(Color.Transparent)
}

private fun toUIColor(color: Color): UIColor = UIColor(
    red = color.red.toDouble(),
    green = color.green.toDouble(),
    blue = color.blue.toDouble(),
    alpha = color.alpha.toDouble()
)