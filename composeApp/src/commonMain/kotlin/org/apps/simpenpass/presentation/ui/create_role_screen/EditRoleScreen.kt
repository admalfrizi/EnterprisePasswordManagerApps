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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.AddRoleRequest
import org.apps.simpenpass.models.request.UpdateRoleMemberGroupRequest
import org.apps.simpenpass.models.request.UpdateRoleNameRequest
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.addGroupComponents.AddMemberLoading
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.groupComponents.GroupLoadingShimmer
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.delete_ic
import resources.edit_group_name_ic

@Composable
fun EditRoleScreen(
    navController: NavController,
    editRoleViewModel: EditRoleViewModel = koinViewModel(),
    groupId: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val editRoleState by editRoleViewModel.editRoleState.collectAsStateWithLifecycle()
    val updateRoleMemberState by editRoleViewModel.editRoleMemberState.collectAsStateWithLifecycle()
    val listRoleState by editRoleViewModel.roleState.collectAsStateWithLifecycle()
    val listMemberState by editRoleViewModel.memberState.collectAsStateWithLifecycle()
    val deleteRoleState by editRoleViewModel.deleteRoleState.collectAsStateWithLifecycle()

    var nameRole = remember { mutableStateOf("") }
    var isEditNameRole = remember { mutableStateOf(false) }
    var roleId = remember { mutableStateOf(0) }
    var toEditRoleId = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(groupId){
        if(groupId.isNotEmpty() && groupId != "{groupId}") {
            editRoleViewModel.getMemberDataGroup(groupId)
            editRoleViewModel.getListRolePositionData(groupId)
        }
    }

    if(updateRoleMemberState.isSuccess){
        setToast(updateRoleMemberState.msg!!)
        editRoleViewModel.getMemberDataGroup(groupId)
        editRoleViewModel.getListRolePositionData(groupId)
    }

    if(editRoleState.isSuccess){
        editRoleViewModel.getListRolePositionData(groupId)
        editRoleViewModel.getMemberDataGroup(groupId)
    }

    if(deleteRoleState.isSuccess){
        editRoleViewModel.getListRolePositionData(groupId)
        editRoleViewModel.getMemberDataGroup(groupId)
    }

    if(isEditNameRole.value){
        UpdateRoleNameDialog(
            nameRole,
            toEditRoleId,
            editRoleViewModel = editRoleViewModel,
            updateRoleState = editRoleState,
            listRoleState = listRoleState,
            onDismissRequest = {
                isEditNameRole.value = false
            }
        )
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color(0xFFF1F1F1),
        sheetContent = {
            BottomSheetContent(
                sheetState,
                editRoleViewModel,
                scope,
                groupId,
                roleId,
                toEditRoleId,
                nameRole,
                isEditNameRole
            )
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = sheetState
    ){
        OverlayContent(
            navController,
            groupId,
            interactionSource,
            nameRole,
            roleId,
            scope,
            sheetState,
            listRoleState.listRoleMember!!,
            listRoleState,
            updateRoleMemberState,
            listMemberState,
            editRoleViewModel
        )
    }
}

@Composable
fun OverlayContent(
    navController: NavController,
    groupId: String,
    interactionSource: MutableInteractionSource,
    nameRole: MutableState<String>,
    roleId: MutableState<Int>,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    roleList: List<RoleGroupData>,
    roleState: ListRoleState,
    updateRoleMemberState: UpdateRoleMemberState,
    memberState: ListMemberState,
    editRoleViewModel: EditRoleViewModel
) {
    var roleNameEdt by remember { mutableStateOf("") }
    var roleFocus by remember { mutableStateOf(false) }
    var isEditPopUp by remember { mutableStateOf(false) }
    var posisiId = remember { mutableStateOf(-1) }
    var memberId by remember { mutableStateOf(0) }

    if(isEditPopUp){
        EditRoleMemberPopUp(
            onDismissRequest = {
                isEditPopUp = false
            },
            roleState,
            updateRoleMemberState,
            editRoleViewModel,
            groupId,
            posisiId,
            memberId
        )
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).imePadding(),
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
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Column(
                    modifier = Modifier.fillMaxSize().align(Alignment.TopCenter)
                ) {
                    Text(
                        "Isi Data Posisi",
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 13.dp),
                        style = MaterialTheme.typography.subtitle2,
                        color = secondaryColor
                    )

                    if(roleState.isLoading){
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    if (roleList.isEmpty() && !roleState.isLoading) {
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
                    }

                    if(roleList.isNotEmpty() && !roleState.isLoading){
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(roleList) { item ->
                                Box(
                                    modifier = Modifier.background(color = Color.White).clickable {
                                        scope.launch {
                                            sheetState.show()
                                        }
                                        nameRole.value = item.nmPosisi
                                        roleId.value = item.id
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(vertical = 15.dp, horizontal = 16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            item.nmPosisi,
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
                        modifier = Modifier.height(8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Text(
                                "Anggota Grup",
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    bottom = 8.dp,
                                    top = 13.dp
                                ),
                                style = MaterialTheme.typography.subtitle2,
                                color = secondaryColor
                            )
                        }

                        when (memberState.isLoading) {
                            true -> {
                                item {
                                    GroupLoadingShimmer()
                                    GroupLoadingShimmer()
                                    GroupLoadingShimmer()
                                }
                            }
                            false -> {
                                items(memberState.listMember!!) { item ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth()
                                            .background(color = Color.White)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 8.dp
                                            ).fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier.background(
                                                    Color(0xFF78A1D7),
                                                    CircleShape
                                                ).size(65.dp)
                                            ) {
                                                Text(
                                                    text = profileNameInitials(item.nama_anggota),
                                                    style = MaterialTheme.typography.h5.copy(
                                                        fontSize = 20.sp
                                                    ),
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
                                            IconButton(
                                                onClick = {
                                                    isEditPopUp = true
                                                    posisiId.value = item.posisiId ?: -1
                                                    memberId = item.id
                                                },
                                                content = {
                                                    Image(
                                                        painterResource(Res.drawable.edit_group_name_ic),
                                                        "",
                                                        colorFilter = ColorFilter.tint(secondaryColor)
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

                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).background(Color.White)
                ) {
                    Text(
                        "Form Tambah Data Posisi",
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 13.dp),
                        style = MaterialTheme.typography.subtitle2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    CustomTextField(
                        interactionSource = interactionSource,
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().onFocusChanged { focusState -> roleFocus = focusState.isFocused },
                        labelHints = "Nama Posisi",
                        value = roleNameEdt,
                        leadingIcon = null,
                        onValueChange = { roleNameEdt = it},
                        isFocus = roleFocus,
                        focusColor = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )
                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        onClick = {
                            editRoleViewModel.addRoleGroup(AddRoleRequest(roleNameEdt),groupId)
                            roleNameEdt = ""
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
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun UpdateRoleNameDialog(
    nameRole: MutableState<String>,
    roleId: MutableState<Int>,
    onDismissRequest: () -> Unit,
    updateRoleState: EditRoleState,
    listRoleState: ListRoleState,
    editRoleViewModel: EditRoleViewModel,
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        },
    ){
        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ){
            Column(
                modifier = Modifier.padding(24.dp)
            ){
                Text(
                    "Edit Nama Posisi",
                    style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                FormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nameRole.value,
                    labelHints = "Update Nama Role Disini...",
                    leadingIcon = null,
                    onValueChange = {
                        nameRole.value = it
                    }
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        editRoleViewModel.updateRoleName(
                            roleId.value,
                            UpdateRoleNameRequest(
                                nameRole.value,
                            )
                        )
                        if(!updateRoleState.isLoading) {
                            onDismissRequest()
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
                    content = {
                        if(updateRoleState.isLoading){
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                "Ubah",
                                style = MaterialTheme.typography.h6,
                                color = fontColor1
                            )
                        }

                    }
                )
            }
        }
    }
}


@Composable
fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    editRoleViewModel: EditRoleViewModel,
    scope: CoroutineScope,
    groupId: String,
    roleId: MutableState<Int>,
    toEditRoleId : MutableState<Int>,
    nameRole: MutableState<String>,
    isEditRoleName: MutableState<Boolean>
) {
    val detailRoleState by editRoleViewModel.detailRoleState.collectAsStateWithLifecycle()

    LaunchedEffect(sheetState.isVisible){
        if(roleId.value != 0){
            editRoleViewModel.getDetailRoleGroup(roleId.value)
        }
    }

    if(!sheetState.isVisible){
        roleId.value = 0
        editRoleViewModel.clearDetailRoleGroup()
    }

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
            if(detailRoleState.roleData != null){
                Text(detailRoleState.roleData?.nmPosisi!!, modifier = Modifier.weight(1f).fillMaxWidth(), style = MaterialTheme.typography.h6.copy(fontSize = 16.sp), color = secondaryColor)
            }

            if(detailRoleState.isLoading){
                Box(
                    modifier = Modifier
                        .width(58.dp).height(24.dp)
                        .shimmer()
                        .background(Color.LightGray, RoundedCornerShape(4.dp)),
                )
            }

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
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            if(detailRoleState.isLoading){
                item{
                    AddMemberLoading()
                    AddMemberLoading()
                    AddMemberLoading()
                }
            }


            if(detailRoleState.roleData != null && sheetState.isVisible){
                items(detailRoleState.roleData?.anggotaGrup!!){ item ->
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
                                    text = profileNameInitials(item.name),
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
                                    item.name,
                                    style = MaterialTheme.typography.h6,
                                    color = secondaryColor
                                )
                                Spacer(
                                    modifier = Modifier.height(5.dp)
                                )
                                Text(
                                    item.email,
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
                }
            }

            if(detailRoleState.roleData?.anggotaGrup?.isEmpty() == true && !detailRoleState.isLoading){
                item{
                    EmptyWarning(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        warnTitle = "Tidak Ada Anggota pada Posisi Ini",
                        warnText = "Silahkan tambah anggota untuk posisi ini.",
                        isEnableBtn = false,
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = {
                nameRole.value = detailRoleState.roleData?.nmPosisi!!
                toEditRoleId.value = detailRoleState.roleData?.id!!
                isEditRoleName.value = true
                scope.launch {
                    sheetState.hide()
                }
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(btnColor),
            content = {
                Text(
                    "Ubah Nama",
                    style = MaterialTheme.typography.h6,
                    color = fontColor1
                )
            }
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = {
                editRoleViewModel.deleteRoleGroup(roleId.value,groupId)
                scope.launch {
                    sheetState.hide()
                }
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(Color.Red),
            content = {
                Text(
                    "Hapus Role Ini",
                    style = MaterialTheme.typography.h6,
                    color = fontColor1
                )
            }
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}

@Composable
fun EditRoleMemberPopUp(
    onDismissRequest: () -> Unit,
    roleState: ListRoleState,
    updateRoleMemberState: UpdateRoleMemberState,
    editRoleViewModel: EditRoleViewModel,
    groupId: String,
    posisiId: MutableState<Int>,
    memberId: Int,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Ubah Posisi Anggota",
                        style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    if(roleState.listRoleMember?.isEmpty() == true){
                        EmptyWarning(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            warnTitle = "Data Posisi Grup ini Kosong !",
                            warnText = "Silahkan buat posisi untuk setiap anggota grup.",
                            isEnableBtn = false,
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        items(roleState.listRoleMember!!){ item ->
                            Box(
                                modifier = Modifier.fillMaxWidth().selectable(
                                    selected = posisiId.value == item.id,
                                    onClick = {
                                        posisiId.value = item.id
                                    },
                                    role = Role.RadioButton
                                )
                            ){
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text(
                                        item.nmPosisi,
                                        style = MaterialTheme.typography.caption,
                                        color = secondaryColor
                                    )
                                    RadioButton(
                                        selected = posisiId.value == item.id,
                                        onClick = null,
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = secondaryColor
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = posisiId.value != -1,
                        onClick = {
                            editRoleViewModel.updateRoleMemberGroup(
                                groupId,
                                UpdateRoleMemberGroupRequest(
                                    memberId,
                                    posisiId.value
                                )
                            )
                            if(!updateRoleMemberState.isLoading) {
                                onDismissRequest()
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.elevation(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
                        content = {
                            if(updateRoleMemberState.isLoading){
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    "Ubah",
                                    style = MaterialTheme.typography.h6,
                                    color = fontColor1
                                )
                            }

                        }
                    )
                }
            }
        }
    )
}