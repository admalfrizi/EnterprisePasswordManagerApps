package org.apps.simpenpass.di

import org.apps.simpenpass.presentation.ui.auth.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AuthViewModel(
            repo = get()
        )
    }
}