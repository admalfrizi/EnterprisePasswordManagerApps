package org.apps.simpenpass.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            appModules, ktorModules,repoModule
        )
    }
}