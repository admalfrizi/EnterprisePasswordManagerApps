package org.apps.simpenpass.di

import okio.Path.Companion.toPath
import org.apps.simpenpass.utils.createDataStore
import org.apps.simpenpass.DataUserStore
import org.apps.simpenpass.data.source.localStorage.DataPrefFunc
import org.apps.simpenpass.utils.dataStoreFileName
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import splitties.init.directBootCtx

actual fun platformModule() = module {
//    single<DataPrefFunc> {
//        DataUserStore(androidContext())
//    }
}

actual fun valuePathData(): String {
    return directBootCtx.filesDir.resolve("datastore/$dataStoreFileName").absolutePath
}