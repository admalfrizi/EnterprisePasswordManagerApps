package org.apps.simpenpass.di

import org.apps.simpenpass.data.repository.ForgotPassRepository
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.koin.dsl.module

val repoModule = module {
    factory<UserRepository> {
        UserRepository(
            remoteUserSources = get(),
            localData = get()
        )
    }

    factory<PassRepository> {
        PassRepository(
            remotePassSources = get(),
            localData = get()
        )
    }

    factory<GroupRepository> {
        GroupRepository(
            remoteGroupSources = get(),
            remoteMemberDataSources = get(),
            localData = get()
        )
    }

    factory<MemberGroupRepository> {
        MemberGroupRepository(
            remoteMemberDataSources = get(),
            localData = get()
        )
    }

    factory<ForgotPassRepository> {
        ForgotPassRepository(
            remoteResetPassSources = get()
        )
    }
}