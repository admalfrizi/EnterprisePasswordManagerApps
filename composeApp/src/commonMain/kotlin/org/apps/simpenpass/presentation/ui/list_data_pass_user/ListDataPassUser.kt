package org.apps.simpenpass.presentation.ui.list_data_pass_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.menu_ic

@Composable
fun ListDataPassUser(
    navController: NavController,
    listDataViewModel: ListDataViewModel = koinViewModel()
) {
    val state by listDataViewModel.listDataState.collectAsState()

    var isDropdownShow by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                title = {
                    Text(
                        "Data Password Anda"
                    )
                },
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        content = {
                            Image(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            isDropdownShow = true
                        },
                        content = {
                            Image(
                                painterResource(Res.drawable.menu_ic),
                                "",
                                colorFilter = ColorFilter.tint(Color.White))}
                    )

                    DropdownMenu(
                        expanded = isDropdownShow,
                        onDismissRequest = { isDropdownShow = false }
                    ) {
                        DropdownMenuItem(
                            content = {
                                Text(text = "Hapus Data Password")
                            },
                            onClick = {
                            }
                        )
                        DropdownMenuItem(
                            content = {
                                Text(text = "Tambah Data Baru")
                            },
                            onClick = {
                            }
                        )
                    }
                }
            )
        },
        content = {
            if(state.data.isEmpty() && !state.isLoading){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    EmptyWarning(
                        modifier = Modifier.fillMaxWidth(),
                        warnTitle = "Data Password anda Kosong",
                        warnText = "Silahkan membuat Data Password yang baru !",
                        isEnableBtn = true,
                        btnTxt = "Buat Data Password Baru",
                        onSelect = {

                        }
                    )
                }
            }

            if(state.data.isNotEmpty()){
                LazyColumn {
                    items(state.data){ item ->
                        DataPassHolder(item)
                    }
                }
            }

            if(state.isLoading){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}