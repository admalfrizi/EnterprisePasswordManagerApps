package org.apps.simpenpass.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.apps.simpenpass.utils.dataStoreFileName
import org.koin.dsl.module
import splitties.init.directBootCtx

actual fun platformModule() = module {
    single<HttpClientEngine> { OkHttp.create() }
}

//actual fun valuePathData(): String {
//    return directBootCtx.filesDir.resolve("datastore/$dataStoreFileName").absolutePath
//}