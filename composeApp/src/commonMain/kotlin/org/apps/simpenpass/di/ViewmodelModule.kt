package org.apps.simpenpass.di

import org.apps.simpenpass.presentation.ui.add_group.AddGroupViewModel
import org.apps.simpenpass.presentation.ui.auth.AuthViewModel
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormViewModel
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataViewModel
import org.apps.simpenpass.presentation.ui.main.group.GroupViewModel
import org.apps.simpenpass.presentation.ui.main.home.HomeViewModel
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

    viewModel {
        HomeViewModel(
            userRepo = get(),
            passRepo = get(),
            groupRepo = get()
        )
    }

    viewModel {
        FormViewModel(
            repo = get()
        )
    }

    viewModel {
        ListDataViewModel(
            repo = get()
        )
    }

    viewModel {
        GroupViewModel(
            repoGroup = get(),
            repoMemberGroupRepository = get()
        )
    }

    viewModel {
        AddGroupViewModel(
            repoMember = get()
        )
    }
}