package org.apps.simpenpass.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module


@OptIn(ExperimentalSerializationApi::class)
val ktorModules = module {
    single {
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
//            install(Auth){
//                bearer {
//                    loadTokens {
//                        BearerTokens(
//                            accessToken = tokenData(tokenData = get()) ?: "",
//                            refreshToken = "" // No need for refreshToken
//                        )
//                    }
//                }
//            }
        }.also { Napier.base(DebugAntilog()) }
    }
}

//suspend fun tokenData(tokenData: LocalStoreData): String? {
//    val cachedToken = MutableStateFlow<String?>("")
//
//    tokenData.getToken.collect {
//        cachedToken.value = it
//    }
//
//    val token = cachedToken.value
//
//    return token
//}