package org.apps.simpenpass

import androidx.compose.ui.window.ComposeUIViewController
import org.apps.simpenpass.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin {  }
    }
) {
    App()
}