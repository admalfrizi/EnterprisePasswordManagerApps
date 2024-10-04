package org.apps.simpenpass.presentation.ui.list_data_pass_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import org.apps.simpenpass.models.DataPass
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.menu_ic

@Composable
fun ListDataPassUser(navController: NavController) {
    val dataList = listOf(
        DataPass(1,"Nama Ini", "adam@gmail.com"),
        DataPass(2,"Ini fewfJuga", "whdkw4t4t@gmail.com"),
        DataPass(3, "In4t4t4i Juga", "whdkw@gmail.554tcom"),
        DataPass(4,"Ihthtrhni Juga", "whdkgrgw@gmail.com"),
        DataPass(5,"Ini fewfJuga", "whdkw4t4t@gmail.com"),
    )

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
            if(dataList.isEmpty()){
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
            } else {
                LazyColumn {
                    items(dataList){ item ->
                        DataPassHolder(item)
                    }
                }
            }
        }
    )
}