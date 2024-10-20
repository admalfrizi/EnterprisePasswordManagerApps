package org.apps.simpenpass.di

import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import org.apps.simpenpass.data.source.remoteData.RemoteMemberDataSources
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.data.source.remoteData.RemoteResetPassSources
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.koin.dsl.module

val remoteDataModule = module {
    single {
        RemoteUserSources(
            httpClient = get()
        )
    }

    single {
        RemotePassDataSources(
            httpClient = get()
        )
    }

    single {
        RemoteGroupDataSources(
            httpClient = get()
        )
    }

    single {
        RemoteMemberDataSources(
            httpClient = get()
        )
    }

    single {
        RemoteResetPassSources(
            httpClient = get()
        )
    }
}