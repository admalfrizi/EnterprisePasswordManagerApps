package org.apps.simpenpass.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.coroutines.InternalCoroutinesApi
import okio.Path.Companion.toPath

private lateinit var dataStore: DataStore<Preferences>
private val lock = SynchronizedObject()

//fun createDataStore(producePath: () -> String): DataStore<Preferences> = synchronized(lock) {
//    if(::dataStore.isInitialized) {
//        dataStore
//    } else {
//        PreferenceDataStoreFactory.createWithPath(
//            produceFile = { producePath().toPath() }
//        )
//    }
//}

fun getDataStore(producePath: () -> String): DataStore<Preferences> {
    return synchronized(lock) {
        if (::dataStore.isInitialized) {
            dataStore
        } else {
            PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            ).also { dataStore = it }
        }
    }
}

internal const val dataStoreFileName = "data.preferences_pb"