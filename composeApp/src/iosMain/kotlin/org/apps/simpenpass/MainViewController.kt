package org.apps.simpenpass

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.apps.simpenpass.Platform
import org.apps.simpenpass.data.remoteData.createHttpClient
import org.apps.simpenpass.style.secondaryColor

fun MainViewController() = ComposeUIViewController {
    App(
        client = createHttpClient(Darwin.create())
    )
}