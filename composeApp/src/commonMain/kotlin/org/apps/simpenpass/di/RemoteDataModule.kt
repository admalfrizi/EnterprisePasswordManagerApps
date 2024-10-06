package org.apps.simpenpass.di

import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.koin.dsl.module

val remoteDataModule = module {
    single {
        RemoteUserSources(
            httpClient = get()
        )
    }
}