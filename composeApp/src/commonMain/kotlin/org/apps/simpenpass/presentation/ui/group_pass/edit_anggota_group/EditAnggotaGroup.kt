package org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.request.UpdateAdminMemberGroupRequest
import org.apps.simpenpass.presentation.components.addGroupComponents.AddMemberLoading
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.getScreenHeight
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_member_ic
import resources.delete_ic
import resources.menu_ic
import resources.role_change

@OptIn(InternalVoyagerApi::class)
@Composable
fun EditAnggotaGroup(
    navController: NavController,
    editAnggotaViewModel: EditAnggotaGroupViewModel = koinViewModel(),
    bottomEdgeColor: MutableState<Color>
) {
    val groupState by editAnggotaViewModel.editAnggotaState.collectAsState()
    val itemsData = groupState.listMember
    val height = getScreenHeight().value.toInt()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    var isSelectionMode = remember {
        mutableStateOf(false)
    }

    val selectedItems = remember {
        mutableStateListOf<UpdateAdminMemberGroupRequest>()
    }

    val resetSelectionMode = {
        isSelectionMode.value = false
        selectedItems.clear()
    }

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(
        key1 = isSelectionMode,
        key2 = selectedItems.size
    ){
        if(isSelectionMode.value && selectedItems.isEmpty()){
            isSelectionMode.value = false
        }
    }

    BackHandler(
        enabled = isSelectionMode.value,
        onBack = {
            resetSelectionMode()
        }
    )

    bottomEdgeColor.value = Color.White

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = sheetState,
        sheetBackgroundColor = Color(0xFFF1F1F1),
        sheetContent = {
            OptionMenu(
                sheetState,
                snackBarHostState,
                scope,
                isSelectionMode,
                groupState,
                selectedItems
            )
        },
        content = {
            ScaffoldContent(
                navController = navController,
                snackBarHostState,
                itemsData,
                scope,
                sheetState,
                groupState,
                editAnggotaViewModel,
                height,
                isSelectionMode = isSelectionMode.value,
                selectedItems = selectedItems,
                resetSelectionMode = resetSelectionMode
            )
        }
    )
}

@Composable
fun ScaffoldContent(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    itemsData: List<MemberGroupData>?,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    groupState: EditAnggotaState,
    editAnggotaGroupViewModel: EditAnggotaGroupViewModel,
    height: Int,
    selectedItems: MutableList<UpdateAdminMemberGroupRequest>,
    isSelectionMode: Boolean,
    resetSelectionMode: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelectionMode) Color(0xFF001530) else secondaryColor // Color changes
    )

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        floatingActionButton = {
            if(isSelectionMode && selectedItems.isNotEmpty()){
                FloatingActionButton(
                    backgroundColor = Color(0xFF1E78EE),
                    onClick = {
                        editAnggotaGroupViewModel.updateAdminMember(selectedItems)
                    },
                    content = {
                        Icon(
                            Icons.Default.Check,
                            "",
                            tint = Color.White
                        )
                    }
                )
            } else {
                null
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                backgroundColor = backgroundColor,
                title = {
                    if(isSelectionMode){
                        Text("Silahkan Pilih Anggota Anda !")
                    } else {
                        Text("Edit Anggota")
                    }
                },
                elevation = 0.dp,
                navigationIcon = {
                    if(isSelectionMode){
                        IconButton(
                            onClick = resetSelectionMode,
                            content = {
                                Image(
                                    Icons.Default.Clear,
                                    "",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        )
                    } else {
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

                },
                actions = {
                    if(!isSelectionMode){
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
                    }

                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ){
        LazyColumn {
            if(groupState.isLoading){
                item{
                    repeat(height / 82) {
                        AddMemberLoading()
                    }
                }
            }

            if(groupState.listMember.isNotEmpty() == true && !groupState.isLoading){
                items(groupState.listMember){ item ->
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
                                    text = profileNameInitials(item.nama_anggota),
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
                                    item.nama_anggota,
                                    style = MaterialTheme.typography.h6,
                                    color = secondaryColor
                                )
                                Text(
                                    item.email_anggota,
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                                Spacer(
                                    modifier = Modifier.height(9.dp)
                                )
                                Text(
                                    item.nmPosisi ?: "Tidak Ada Posisi",
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                            }
                            if(itemsData?.size != 1 && !isSelectionMode){
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
                            if (isSelectionMode){
                                Checkbox(
                                    onCheckedChange = {
                                        if(selectedItems.contains(UpdateAdminMemberGroupRequest(item.id,item.isGroupAdmin!!))){
                                            selectedItems.remove(UpdateAdminMemberGroupRequest(item.id,item.isGroupAdmin!!))
                                            when(item.isGroupAdmin == true){
                                                true -> {
                                                    selectedItems.add(UpdateAdminMemberGroupRequest(item.id,false))
                                                }
                                                false -> {
                                                    selectedItems.add(UpdateAdminMemberGroupRequest(item.id,true))
                                                }
                                            }
                                        } else {
                                            when(selectedItems.contains(UpdateAdminMemberGroupRequest(item.id, true))){
                                                true -> {
                                                    selectedItems.remove(UpdateAdminMemberGroupRequest(item.id,true))
                                                    selectedItems.add(UpdateAdminMemberGroupRequest(item.id,false))
                                                }
                                                false -> {
                                                    selectedItems.remove(UpdateAdminMemberGroupRequest(item.id,false))
                                                    selectedItems.add(UpdateAdminMemberGroupRequest(item.id,true))
                                                }
                                            }
                                        }
                                    },
                                    checked = selectedItems.contains(UpdateAdminMemberGroupRequest(item.id,true)),
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF78A1D7), uncheckedColor = secondaryColor)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptionMenu(
    sheetState: ModalBottomSheetState,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    isSelectionMode: MutableState<Boolean>,
    groupState: EditAnggotaState,
    listItem: MutableList<UpdateAdminMemberGroupRequest>
) {
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
                when(groupState.listMember.size > 1){
                    true -> {
                        isSelectionMode.value = true
                        groupState.listMember.forEach {
                            listItem.add(UpdateAdminMemberGroupRequest(it.id,it.isGroupAdmin!!))
                        }
                    }
                    false -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Maaf Anda adalah Admin Grup Ini !")
                        }

                    }
                }

                scope.launch {
                    sheetState.hide()
                }
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
                    "Ubah Admin Anggota",
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