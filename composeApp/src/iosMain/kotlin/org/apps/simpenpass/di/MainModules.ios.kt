package org.apps.simpenpass.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.apps.simpenpass.utils.dataStoreFileName
import org.apps.simpenpass.utils.getDataStore
import org.koin.dsl.module

actual fun platformModule() = module {
    single<HttpClientEngine> { Darwin.create() }
    single {
        getDataStore {
            val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )

            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    }
}

//actual fun valuePathData(): String {
//    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
//        directory = NSDocumentDirectory,
//        inDomain = NSUserDomainMask,
//        appropriateForURL = null,
//        create = false,
//        error = null
//    )
//
//    return requireNotNull(documentDirectory).path + "/$dataStoreFileName"
//}