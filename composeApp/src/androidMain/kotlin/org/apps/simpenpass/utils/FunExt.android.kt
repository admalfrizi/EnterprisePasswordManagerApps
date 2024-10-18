package org.apps.simpenpass.utils

import android.content.Context
import android.widget.Toast

lateinit var appContext: Context

fun initializeAppContext(context: Context) {
    appContext = context
}

actual fun setToast(message: String) {
    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
}