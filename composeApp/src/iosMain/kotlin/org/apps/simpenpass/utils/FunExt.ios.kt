package org.apps.simpenpass.utils

import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIPasteboard

fun getViewController(): UIViewController? {
    return UIApplication.sharedApplication.keyWindow?.rootViewController
}

actual fun setToast(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )

    val okAction = UIAlertAction.actionWithTitle(
        title = "OK",
        style = UIAlertActionStyleDefault,
        handler = null
    )

    alert.addAction(okAction)

    getViewController()?.presentViewController(alert, animated = true, completion = null)
}

actual fun copyText(text: String) {
    UIPasteboard.generalPasteboard().string = text
}