package org.apps.simpenpass.di

import org.apps.simpenpass.presentation.ui.add_group.AddGroupViewModel
import org.apps.simpenpass.presentation.ui.auth.AuthViewModel
import org.apps.simpenpass.presentation.ui.create_data_pass.group.FormPassGroupViewModel
import org.apps.simpenpass.presentation.ui.create_data_pass.users.FormViewModel
import org.apps.simpenpass.presentation.ui.create_role_screen.EditRoleViewModel
import org.apps.simpenpass.presentation.ui.group_pass.GroupDetailsViewModel
import org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group.EditAnggotaGroupViewModel
import org.apps.simpenpass.presentation.ui.group_pass.pass_data_group_detail.PassDataDetailsViewModel
import org.apps.simpenpass.presentation.ui.list_data_pass_user.ListDataViewModel
import org.apps.simpenpass.presentation.ui.main.SplashViewModel
import org.apps.simpenpass.presentation.ui.main.group.GroupViewModel
import org.apps.simpenpass.presentation.ui.main.home.HomeViewModel
import org.apps.simpenpass.presentation.ui.main.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AuthViewModel(
            userRepo = get(),
            forgotPassRepo = get()
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
            konnection = get()
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
            konnection = get()
        )
    }

    viewModel {
        AddGroupViewModel(
            repoMember = get(),
            groupRepo = get()
        )
    }

    viewModel {
        EditRoleViewModel(
            repoMemberGroup = get(),
            repoGroup = get()
        )
    }

    viewModel {
        FormPassGroupViewModel(
            repoPassDataGroup = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        GroupDetailsViewModel(
            savedStateHandle = get(),
            repoPassDataGroup = get(),
            repoMemberGroup = get(),
            repoGroup = get()
        )
    }

    viewModel {
        PassDataDetailsViewModel(
            repoPassDataGroup = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        EditAnggotaGroupViewModel(
            repo = get()
        )
    }

    viewModel {
        SplashViewModel(
            localStoreData = get()
        )
    }
}