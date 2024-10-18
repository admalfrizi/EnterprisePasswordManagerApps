package org.apps.simpenpass

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.apps.simpenpass.di.initKoin

fun main() = application {
    initKoin {

    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "EnterprisePasswordManagerApps",
    ) {
        App()
    }
}