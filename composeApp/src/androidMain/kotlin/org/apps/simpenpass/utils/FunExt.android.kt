package org.apps.simpenpass.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

lateinit var appContext: Context

fun initializeAppContext(context: Context) {
    appContext = context
}

actual fun setToast(message: String) {
    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
}

actual fun copyText(text: String) {
    val clipboard = appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
}

@Composable
actual fun getScreenHeight(): Dp = LocalConfiguration.current.screenHeightDp.dp