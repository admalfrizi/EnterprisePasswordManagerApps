package org.apps.simpenpass.presentation.ui.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import org.apps.simpenpass.presentation.components.DialogLoading
import org.apps.simpenpass.presentation.components.DialogWarning
import org.apps.simpenpass.presentation.components.profileComponents.HeaderContainer
import org.apps.simpenpass.presentation.components.profileComponents.SettingsListHolder
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.primaryColor
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.arrow_right_ic

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsState()
//    var name: String? by remember { mutableStateOf(null) }
//    var email: String? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit){
        profileViewModel.getUserData()
        profileViewModel.getUserToken()
    }

//    Napier.d("Token: ${profileState.token}")
//    Napier.d("User: name:${profileState.userData?.name}, email:${profileState.userData?.email}")

    Scaffold(
        backgroundColor = Color(0xFFF1F1F1),
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                elevation = 0.dp,
                title = {
                    Text(
                        "Data Profil",
                        style = MaterialTheme.typography.h6,
                        color = fontColor1
                    )
                }
            )
        },
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

                SettingListView(navController, profileState,profileViewModel)
            }
        }
    )
}



@Composable
fun SettingListView(navController: NavController, profileState: ProfileState, profileViewModel: ProfileViewModel) {
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
        navController.navigate(Screen.Auth.route){
            popUpTo("root"){
                inclusive = true
            }
        }
    }

    if(profileState.isLoading){
        DialogLoading {}
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
        Column() {
            SettingsListHolder("Ubah Email", {})
            SettingsListHolder("Ubah Password", {})
            SettingsListHolder("Keamanan Biometrik", {})
        }
        Text(
            "Informasi",
            modifier = Modifier.padding(vertical = 11.dp, horizontal = 16.dp).fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Column() {
            SettingsListHolder("Riwayat Data Password", {})
            SettingsListHolder("Export/Import Data Password", {})
            SettingsListHolder("Informasi Aplikasi", {})
        }
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        SettingsListHolder("Logout") {
           isLogoutWarningShow = true
        }
    }
}