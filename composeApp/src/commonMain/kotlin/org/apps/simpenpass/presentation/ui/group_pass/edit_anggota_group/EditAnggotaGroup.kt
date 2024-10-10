package org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.add_member_ic
import resources.delete_ic
import resources.menu_ic
import resources.role_change

@Composable
fun EditAnggotaGroup(navController: NavController) {
    val itemsData = listOf(
        MemberGroupData(1, "Nama Orang", "Email", true, "Posisi"),
        MemberGroupData(2, "Nama Orang", "Email", false, "Posisi"),
        MemberGroupData(3, "Nama Orang", "Email", false, "Posisi"),
        )
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = sheetState,
        sheetBackgroundColor = Color(0xFFF1F1F1),
        sheetContent = {
            OptionMenu(sheetState,scope)
        },
        content = {
            ScaffoldContent(
                navController = navController,
                itemsData,
                scope,
                sheetState
            )
        }
    )
}

@Composable
fun OptionMenu(sheetState: ModalBottomSheetState, scope: CoroutineScope) {
    Column {
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Box(
            modifier = Modifier.fillMaxWidth().clickable {

            }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(Res.drawable.add_member_ic),
                    ""
                )
                Spacer(
                    modifier = Modifier.width(18.dp)
                )
                Text(
                    "Undang Anggota Baru",
                    style = MaterialTheme.typography.subtitle2,
                    color = secondaryColor
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().clickable {

            }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(Res.drawable.role_change),
                    ""
                )
                Spacer(
                    modifier = Modifier.width(18.dp)
                )
                Text(
                    "Ubah Posisi Anggota",
                    style = MaterialTheme.typography.subtitle2,
                    color = secondaryColor
                )
            }
        }
        Spacer(
            modifier = Modifier.height(31.dp)
        )
    }
}

@Composable
fun ScaffoldContent(navController: NavController, itemsData: List<MemberGroupData>, scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                title = { Text("Edit Anggota") },
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
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
                        content = {
                            Image(
                                painterResource(Res.drawable.menu_ic),
                                "",
                                colorFilter = ColorFilter.tint(Color.White),
                            )
                        },
                        onClick = {
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ){

        LazyColumn {
            items(itemsData){ item ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.background(Color(0xFF78A1D7), CircleShape).size(65.dp)
                        ) {
                            Text(
                                text = profileNameInitials(item.nm_people),
                                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(
                            modifier = Modifier.width(27.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                item.nm_people,
                                style = MaterialTheme.typography.h6,
                                color = secondaryColor
                            )
                            Text(
                                item.email,
                                style = MaterialTheme.typography.subtitle1,
                                color = secondaryColor
                            )
                            Spacer(
                                modifier = Modifier.height(9.dp)
                            )
                            Text(
                                item.role_position,
                                style = MaterialTheme.typography.subtitle1,
                                color = secondaryColor
                            )
                        }
                        if(itemsData.size != 1){
                            IconButton(
                                onClick = {

                                },
                                content = {
                                    Image(
                                        painterResource(Res.drawable.delete_ic),""
                                    )
                                }
                            )
                        }

                    }
                }
            }
        }

    }
}