package org.apps.simpenpass.di

import org.apps.simpenpass.data.repository.AuthRepository
import org.koin.dsl.module

val repoModule = module {
    factory<AuthRepository> {
        AuthRepository(
            remoteUserSources = get(),
            localData = get()
        )
    }
}