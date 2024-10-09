package org.apps.simpenpass.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    require(
        value = context is Context,
        lazyMessage = { "Context object is required." }
    )

    return getDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}