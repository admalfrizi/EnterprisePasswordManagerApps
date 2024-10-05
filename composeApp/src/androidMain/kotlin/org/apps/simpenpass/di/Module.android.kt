package org.apps.simpenpass.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val appModules = module {
    single<HttpClientEngine> { OkHttp.create() }
}