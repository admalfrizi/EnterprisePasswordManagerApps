package org.apps.simpenpass.di

import org.apps.simpenpass.presentation.ui.auth.AuthViewModel
import org.apps.simpenpass.presentation.ui.main.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AuthViewModel(
            repo = get()
        )

    }

    viewModel {
        ProfileViewModel(
            repo = get()
        )
    }
}