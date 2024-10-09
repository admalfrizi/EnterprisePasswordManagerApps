package org.apps.simpenpass.di

import org.apps.simpenpass.data.repository.UserRepository
import org.koin.dsl.module

val repoModule = module {
    factory<UserRepository> {
        UserRepository(
            remoteUserSources = get(),
            localData = get()
        )
    }
}