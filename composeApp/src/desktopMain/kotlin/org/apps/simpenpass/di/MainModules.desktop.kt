package org.apps.simpenpass.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.apps.simpenpass.utils.dataStoreFileName
import org.apps.simpenpass.utils.getDataStore
import org.koin.dsl.module

actual fun platformModule() = module {
    single<HttpClientEngine> { OkHttp.create() }
    single {
        getDataStore {
            dataStoreFileName
        }
    }
}
