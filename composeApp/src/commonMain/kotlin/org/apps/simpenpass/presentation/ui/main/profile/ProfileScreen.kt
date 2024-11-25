package org.apps.simpenpass.presentation.ui.main.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.presentation.components.DialogLoading
import org.apps.simpenpass.presentation.components.DialogWarning
import org.apps.simpenpass.presentation.components.profileComponents.HeaderContainer
import org.apps.simpenpass.presentation.components.profileComponents.SettingsListHolder
import org.apps.simpenpass.style.secondaryColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = koinViewModel(),
    navigateToLogout: () -> Unit,
    navToChangePass: () -> Unit,
) {
    val profileState by profileViewModel.profileState.collectAsState()

    Scaffold(
        backgroundColor = Color(0xFFF1F1F1),
        modifier = Modifier.fillMaxWidth(),
        content = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                HeaderContainer(
                    nameUser = profileState.userData?.name ?: "",
                    email = profileState.userData?.email ?: ""
                )
                Spacer(
                    modifier= Modifier.height(11.dp)
                )

                SettingListView(navigateToLogout, navToChangePass, profileState,profileViewModel)
            }
        }
    )
}

@Composable
fun SettingListView(
    navigateToLogout: () -> Unit,
    navigateToChangePass: () -> Unit,
    profileState: ProfileState,
    profileViewModel: ProfileViewModel
) {
    var isLogoutWarningShow by remember { mutableStateOf(false) }

    if(isLogoutWarningShow){
        DialogWarning(
            dialogTitle = "Anda akan Logout dari Aplikasi ini !",
            dialogText = "Silahkan untuk Memasukan Kembali Password Anda !",
            onDismissRequest = {isLogoutWarningShow = false},
            onClick = {
                profileViewModel.logout(profileState.token!!)
            }
        )
    }

    if(profileState.isLogout){
        navigateToLogout()
    }

    if(profileState.isLoading){
        isLogoutWarningShow = false
        DialogLoading {}
    }

    if(profileState.isSuccess){
        navigateToChangePass()
        profileState.isSuccess = false
    }

    Column(
        modifier= Modifier.fillMaxWidth()
    ) {
        Text(
            "Pengaturan Profile",
            modifier = Modifier.padding(bottom = 11.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Column {
            SettingsListHolder("Ubah Biodata", onClick = {})
            SettingsListHolder("Ubah Password", onClick =  {
                profileViewModel.sendOtp(profileState.userData?.email!!)
            })
        }
        Text(
            "Pengaturan Aplikasi",
            modifier = Modifier.padding(vertical = 11.dp, horizontal = 16.dp).fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Column {
            SettingsListHolder("Informasi Aplikasi", onClick =  {})
        }
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        SettingsListHolder("Logout", onClick = {
            isLogoutWarningShow = true
        })
    }
}