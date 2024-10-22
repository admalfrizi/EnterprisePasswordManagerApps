package org.apps.simpenpass.presentation.ui.create_role_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.delete_ic

@Composable
fun EditRoleScreen(navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val roleList = listOf(
        RoleGroupData(1, "Dosen",10)
    )
    //val roleList by remember { mutableStateOf(emptyList<RoleGroupData>()) }

    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color(0xFFF1F1F1),
        sheetContent = {
            BottomSheetContent(sheetState,scope)
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = sheetState
    ){
        OverlayContent(navController,interactionSource, scope,sheetState,roleList)
    }
}

@Composable
fun OverlayContent(
    navController: NavController,
    interactionSource: MutableInteractionSource,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    roleList: List<RoleGroupData>
) {
    var roleName by remember { mutableStateOf("") }
    var roleFocus by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        backgroundColor = Color(0xFFF1F1F1),
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                title = {
                    Text(
                        "Edit Posisi"
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
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Isi Data Posisi",
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 13.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = secondaryColor
                )
                if(roleList.isEmpty()){
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        EmptyWarning(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            warnTitle = "Belum ada posisi yang dibuat !",
                            warnText = "Silahkan buat posisi untuk setiap anggota baru dibawah ini.",
                            isEnableBtn = false,
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(roleList) { item ->
                            Box(
                                modifier = Modifier.background(color = Color.White).clickable {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(vertical = 15.dp, horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        item.roleNm,
                                        style = MaterialTheme.typography.h6.copy(fontSize = 12.sp),
                                        color = secondaryColor
                                    )
                                    Text(
                                        "${item.jmlhAnggota} Anggota",
                                        style = MaterialTheme.typography.subtitle2,
                                        color = secondaryColor
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(13.dp)
                )
                CustomTextField(
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().onFocusChanged { focusState -> roleFocus = focusState.isFocused },
                    labelHints = "Nama Posisi",
                    value = roleName,
                    leadingIcon = null,
                    onValueChange = { roleName = it},
                    isFocus = roleFocus,
                    focusColor = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(18.dp)
                )
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    onClick = {

                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
                    content = {
                        Text(
                            "Tambahkan",
                            style = MaterialTheme.typography.h6,
                            color = fontColor1
                        )
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
) {
    Column(
        modifier = Modifier.fillMaxWidth().imePadding()
    ) {
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dosen", modifier = Modifier.weight(1f).fillMaxWidth(), style = MaterialTheme.typography.h6.copy(fontSize = 16.sp), color = secondaryColor)
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                content = {
                    Icon(
                        Icons.Filled.Clear,
                        ""
                    )
                }
            )
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Box(
            modifier = Modifier.fillMaxWidth().background(color = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.background(Color(0xFF78A1D7), CircleShape).size(44.dp)
                ) {
                    Text(
                        text = profileNameInitials("Nama Orang"),
                        style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(
                    modifier = Modifier.width(18.dp)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "Nama Orang",
                        style = MaterialTheme.typography.h6,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )
                    Text(
                        "Email",
                        style = MaterialTheme.typography.subtitle1,
                        color = secondaryColor
                    )
                }
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
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 20.dp),
            onClick = {

            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
            content = {
                Text(
                    "Tambahkan",
                    style = MaterialTheme.typography.h6,
                    color = fontColor1
                )
            }
        )
    }
}