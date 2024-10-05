package org.apps.simpenpass.di

fun initKoin() {
    org.koin.core.context.startKoin {
        modules(
            appModules, ktorModules,repoModule
        )
    }
}