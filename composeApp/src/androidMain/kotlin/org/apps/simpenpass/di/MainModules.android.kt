package org.apps.simpenpass.di

import org.apps.simpenpass.utils.dataStoreFileName
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