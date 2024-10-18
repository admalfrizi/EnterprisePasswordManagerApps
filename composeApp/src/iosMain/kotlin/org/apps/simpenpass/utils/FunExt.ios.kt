package org.apps.simpenpass.utils

import platform.UIKit.UIApplication


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