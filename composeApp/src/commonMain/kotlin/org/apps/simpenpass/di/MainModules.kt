package org.apps.simpenpass.di

import org.apps.simpenpass.data.source.localStorage.DataPrefFunc
import org.apps.simpenpass.data.source.localStorage.LocalStoreData
import org.apps.simpenpass.utils.createDataStore
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(
        platformModule(),
        dataStoreModule,
        ktorModules,
        repoModule,
        remoteDataModule,
        viewModelModule
    )
}

val dataStoreModule = module {
    single {
        createDataStore {
            valuePathData()
        }
    }

    single {
        LocalStoreData(
            dataStore = get()
        )
    }
}


expect fun valuePathData() : String