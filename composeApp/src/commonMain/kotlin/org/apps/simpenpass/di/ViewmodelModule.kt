package org.apps.simpenpass.di

import org.apps.simpenpass.presentation.ui.auth.AuthViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory {
        AuthViewModel(
            repo = get()
        )
    }
}