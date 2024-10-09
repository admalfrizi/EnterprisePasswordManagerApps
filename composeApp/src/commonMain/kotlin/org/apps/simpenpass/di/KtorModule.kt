package org.apps.simpenpass.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.koin.dsl.module


@OptIn(ExperimentalSerializationApi::class)
val ktorModules = module {
    single {
        val localUserStore = LocalStoreData(dataStore = get())

        HttpClient(engine = get()) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        explicitNulls = true
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = localUserStore.getToken() ?: "",
                            refreshToken = "" // No need for refreshToken
                        )
                    }
                }
            }
        }.also { Napier.base(DebugAntilog()) }
    }
}