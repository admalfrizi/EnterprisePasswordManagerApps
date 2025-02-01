package org.apps.simpenpass.presentation.ui.group_pass.settings_group

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.SendDataPassToDecrypt
import org.apps.simpenpass.models.request.UpdatePassDataGroupToDecrypt
import org.apps.simpenpass.models.request.VerifySecurityDataGroupRequest
import org.apps.simpenpass.models.response.GetPassDataGroup
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.addGroupComponents.AddMemberLoading
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.profileComponents.SettingsListHolder
import org.apps.simpenpass.presentation.ui.add_group_security_option.AddGroupSecurityViewModel
import org.apps.simpenpass.presentation.ui.add_group_security_option.FormGroupSecurityOption
import org.apps.simpenpass.presentation.ui.add_group_security_option.decryptPassData
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.CamelliaCrypto
import org.apps.simpenpass.utils.Constants
import org.apps.simpenpass.utils.maskStringAfter3Char
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.profileNameInitials
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.delete_ic
import resources.group_ic

@OptIn(ExperimentalMaterialApi::class, InternalVoyagerApi::class)
@Composable
fun GroupSettingsScreen(
    groupSettingsViewModel: GroupSettingsViewModel = koinViewModel(),
    navToEditRole : (String) -> Unit,
    navToBack : () -> Unit
) {
    var isDismiss = remember { mutableStateOf(false) }
    var isDeleted = remember { mutableStateOf(false) }
    var isPopUp = remember { mutableStateOf(false) }
    var toUpdate = remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var grupName by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val groupState by groupSettingsViewModel.groupSettingsState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var isPopUpToDecrypt = remember { mutableStateOf(false) }
    var listPassDataToDecrypt = remember { mutableListOf<UpdatePassDataGroupToDecrypt>() }
    var listPassDataEncrypted = remember { mutableListOf<GetPassDataGroup>() }
    var securityDataId = remember { mutableStateOf(0) }

    if(groupState.groupData != null){
        grupName = groupState.groupData?.groupDtl?.nm_grup!!
        desc = groupState.groupData?.groupDtl?.desc ?: ""
    }

    val context = LocalPlatformContext.current
    var imgFile by remember { mutableStateOf(ByteArray(0)) }
    var nameImg by remember { mutableStateOf("") }
    val launcher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    imgFile = it.readByteArray(context)
                    nameImg = it.getName(context)!!

                }
            }
        }
    )
    val imagesName = groupState.groupData?.groupDtl?.img_grup
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val focusManager = LocalFocusManager.current
    val isDeleteMode = remember { mutableStateOf(false) }

    if(groupState.isSuccess && !groupState.isLoading){
        setToast("Data Telah Berhasil Diperbaharui !")
    }

    if(groupState.isLoading){
        popUpLoading(isDismiss)
    }

    if(isPopUpToDecrypt.value){
        DecryptToChangeSecurityData(
            onDismissRequest = {
                isPopUpToDecrypt.value = false
                isDeleteMode.value = false
            },
            groupSettingsViewModel,
            isDeleteMode
        )
    }

    if(groupState.isDeleted){
        setToast("Data Keamanan Telah Dihapus")
        isDeleted.value = true
        isPopUpToDecrypt.value = false
        groupState.isDeleted = false
    }

    BackHandler(
        enabled = sheetState.isVisible
    ){
        scope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetGesturesEnabled = false,
        sheetBackgroundColor = Color.White,
        sheetContent = {
            ListSecurityData(
                groupState,
                listPassDataToDecrypt,
                securityDataId,
                scope,
                isPopUp,
                toUpdate,
                isPopUpToDecrypt,
                isDeleted,
                isDeleteMode,
                sheetState,
                groupState.groupId?.toInt()!!,
                groupSettingsViewModel = groupSettingsViewModel
            )
        }
    ){
        Scaffold(
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing).imePadding(),
            backgroundColor = Color(0xFFF1F1F1),
            topBar = {
                TopAppBar(
                    backgroundColor = secondaryColor,
                    title = {
                        Text(
                            "Pengaturan Grup"
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navToBack()
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
            }
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Box(
                    modifier = Modifier.size(99.dp)
                        .background(color = Color(0xFF78A1D7), shape = CircleShape).clickable(
                            interactionSource = interactionSource,
                            indication = ripple(bounded = false)
                        ) {
                            launcher.launch()
                        },
                ){
                    if(imagesName != null && imgFile.isEmpty()){
                        AsyncImage(
                            model = Constants.IMAGE_URL + imagesName,
                            modifier = Modifier.size(99.dp).clip(CircleShape),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        when(imgFile.isNotEmpty()){
                            true -> {
                                AsyncImage(
                                    model = imgFile,
                                    modifier = Modifier.size(99.dp).clip(CircleShape),
                                    contentDescription = "Profile Picture",
                                    contentScale = ContentScale.Crop
                                )
                            }
                            false -> {
                                Text(
                                    if(groupState.groupData?.groupDtl?.nm_grup != null)profileNameInitials(groupState.groupData?.groupDtl?.nm_grup!!) else "",
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 36.sp,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            else -> {}
                        }
                    }
                    Box(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .background(color = Color(0xFF195389), shape = CircleShape)
                            .size(24.dp)
                    ){
                        Image(
                            Icons.Default.Edit,
                            "",
                            modifier = Modifier.size(8.57.dp).align(Alignment.Center),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                ) {
                    BasicTextField(
                        value = grupName,
                        onValueChange = {
                            if(groupState.groupData != null) {
                                groupState.groupData?.groupDtl?.nm_grup = it
                            }

                            grupName = it
                        },
                        modifier = Modifier.padding(0.dp).onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        ),
                        decorationBox = { innerTextField ->
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Image(
                                        painterResource(Res.drawable.group_ic),"", colorFilter = ColorFilter.tint(secondaryColor)
                                    )
                                    Box {
                                        if (grupName.isEmpty()) {
                                            Text(
                                                text = "Isi Nama Grup Ini",
                                                style =  MaterialTheme.typography.caption.copy(color = Color(0xFFABABAB))
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                                Divider(
                                    color = secondaryColor,
                                    thickness = 2.dp,
                                    modifier = Modifier.padding(top = 6.dp) // Adjust the position
                                )
                            }
                        },
                        cursorBrush = SolidColor(secondaryColor),
                        textStyle = MaterialTheme.typography.button.copy(
                            color = secondaryColor
                        ),
                        singleLine = false,
                    )
                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                BasicTextField(
                    value = desc,
                    onValueChange = {
                        if(groupState.groupData != null) {
                            groupState.groupData?.groupDtl?.desc = it
                        }
                        desc = it
                    },
                    textStyle = MaterialTheme.typography.caption.copy(color = secondaryColor),
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth().height(269.dp).padding(horizontal = 16.dp),
                    cursorBrush = SolidColor(secondaryColor),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                ){
                    TextFieldDefaults.OutlinedTextFieldDecorationBox(
                        border = {
                            TextFieldDefaults.BorderBox(
                                enabled = true,
                                interactionSource = interactionSource,
                                colors = TextFieldDefaults.textFieldColors(
                                    unfocusedIndicatorColor = Color.Black,
                                ),
                                shape = RoundedCornerShape(9.dp),
                                isError = false,
                                unfocusedBorderThickness = 1.dp,
                            )
                        },
                        value = desc,
                        innerTextField = it,
                        enabled = true,
                        singleLine = false,
                        interactionSource = interactionSource,
                        visualTransformation = VisualTransformation.None,
                        placeholder = {
                            Text(
                                "Silahkan Tulis Deskripsi Disini....",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.caption.copy(color = Color(0xFFABABAB)),
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            backgroundColor = Color.White,
                            unfocusedIndicatorColor = Color.Black
                        ),
                        contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                            start = 16.dp
                        )
                    )
                }
                Spacer(
                    modifier = Modifier.height(22.dp)
                )
                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClick = {
                        groupSettingsViewModel.updateGroupData(groupState.groupData?.groupDtl?.id.toString(), AddGroupRequest(grupName,desc),imgFile,nameImg)
                        scope.launch {
                            keyboardController?.hide()
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
                    content = {
                        Text(
                            "Update Grup",
                            style = MaterialTheme.typography.h6,
                            color = fontColor1
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                SettingsListHolder(
                    "Edit Role Anggota",
                    prefixTitle = "${groupState.groupData?.totalRole} Posisi",
                    {
                        navToEditRole(groupState.groupId!!)
                    }
                )
                SettingsListHolder(
                    "Tambahkan Keamanan Grup",
                    onClick =  {
                        scope.launch {
                            sheetState.show()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ListSecurityData(
    groupState: GroupSettingsState,
    listPassDataEncrypted: MutableList<UpdatePassDataGroupToDecrypt>,
    securityDataId: MutableState<Int>,
    scope: CoroutineScope,
    isPopUp: MutableState<Boolean>,
    toUpdate: MutableState<Boolean>,
    isPopUpToDecrypt: MutableState<Boolean>,
    isDeleted: MutableState<Boolean>,
    isDeleteMode: MutableState<Boolean>,
    sheetState: ModalBottomSheetState,
    groupId: Int,
    groupDataSecurityViewModel: AddGroupSecurityViewModel = koinInject(),
    groupSettingsViewModel: GroupSettingsViewModel
) {
    val addGroupSecurityDataState = groupDataSecurityViewModel.groupSecurityDataState.collectAsState()
    val securityData = addGroupSecurityDataState.value.securityData

    LaunchedEffect(sheetState.isVisible && !isPopUp.value){
        if(!isPopUp.value && !addGroupSecurityDataState.value.isUpdated && !addGroupSecurityDataState.value.isDeleted){
            groupDataSecurityViewModel.getDataSecurityForGroup(groupId)
        }
    }

    LaunchedEffect(addGroupSecurityDataState.value.passDataGroup.isEmpty()){
        if(addGroupSecurityDataState.value.passDataGroup.isEmpty()){
            groupDataSecurityViewModel.getPassDataGroupEncrypted(groupState.groupId)
        }
    }

    securityDataId.value = securityData?.id ?: 0

    if(groupState.isDecrypted && !toUpdate.value){
        groupDataSecurityViewModel.deleteSecurityDataForGroup(
            groupId,
            securityDataId.value
        )
        groupState.isDecrypted = false
    }

    if(isPopUp.value){
        FormGroupSecurityOption(
            groupId,
            groupState,
            addGroupSecurityDataState.value.passDataGroup,
            isPopUpToDecrypt,
            toUpdate,
            securityData = securityData,
            onDismissRequest = {
                isPopUp.value = false
            },
        )
    }

    if(groupState.isPassVerify){
        when(toUpdate.value){
            true -> {
                isPopUpToDecrypt.value = false
                if(addGroupSecurityDataState.value.passDataGroup.isNotEmpty()){
                    val listDecData = decryptPassData(
                        addGroupSecurityDataState.value.passDataGroup,
                        listPassDataEncrypted,
                        groupState.key
                    )

                    groupSettingsViewModel.sendDataPassToDecrypt(groupState.groupId!!, SendDataPassToDecrypt(listDecData))
                }
            }
            false -> {
                proceedDeleteSecurityData(groupState.groupId!!,groupState.key!!,groupState.passDataGroup,listPassDataEncrypted,groupSettingsViewModel,securityDataId.value)
            }
            else -> {}
        }
        groupState.isPassVerify = false
    }

    if(isDeleted.value){
        groupDataSecurityViewModel.getDataSecurityForGroup(groupId)
        isDeleted.value = false
    }

    if(addGroupSecurityDataState.value.isDeleted){
        isPopUpToDecrypt.value = false
        setToast("Data Keamanan Telah Dihapus")
        groupDataSecurityViewModel.getDataSecurityForGroup(groupId)
        addGroupSecurityDataState.value.isDeleted = false
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
            Text(
                "Data untuk Keamanan Grup",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                color = secondaryColor
            )

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
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            if(addGroupSecurityDataState.value.isLoading){
                AddMemberLoading()
            }
            if(addGroupSecurityDataState.value.securityData != null && sheetState.isVisible && !addGroupSecurityDataState.value.isLoading){
                Box(
                    modifier = Modifier.fillMaxWidth().background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                if(securityData?.typeId == 2) securityData.securityData else "Data Password",
                                style = MaterialTheme.typography.h6,
                                color = secondaryColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )
                            Text(
                                if(securityData?.typeId == 1) maskStringAfter3Char(securityData.securityValue!!) else securityData?.securityValue ?: "",
                                style = MaterialTheme.typography.subtitle1,
                                color = secondaryColor
                            )

                        }
                        Spacer(
                            modifier = Modifier.width(16.dp)
                        )
                        IconButton(
                            onClick = {
                                isPopUpToDecrypt.value = true
                                isDeleteMode.value = true
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


            if(securityData == null && !addGroupSecurityDataState.value.isLoading){
                EmptyWarning(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    warnTitle = "Tidak Ada Data Keamanan pada Grup Ini",
                    warnText = "Tambahkan Data Keamanan untuk Mengamankan Data Password di Grup Ini",
                    isEnableBtn = false,
                )
            }
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = {
                if(securityData != null){
                    toUpdate.value = true
                }

                isPopUp.value = true
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(btnColor),
            content = {
                Text(
                    if(securityData != null) "Ubah Data Keamanan" else "Tambahkan Data Keamanan",
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
fun DecryptToChangeSecurityData(
    onDismissRequest: () -> Unit,
    groupDetailsViewModel: GroupSettingsViewModel,
    isDeleteMode: MutableState<Boolean>
) {
    var passDataDetailsState = groupDetailsViewModel.groupSettingsState.collectAsState()
    var securityData = remember { mutableStateOf("") }
    var securityValue = remember { mutableStateOf("") }

    if(passDataDetailsState.value.dataSecurity == null){
        groupDetailsViewModel.getSecurityData()
    }

    if(passDataDetailsState.value.dataSecurity?.typeId == 2){
        securityData.value = passDataDetailsState.value.dataSecurity?.securityData!!
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        if(isDeleteMode.value == false) "Masukan Data Keamanan Sebelumnya" else "Konfirmasi Data Keamanan Sebelumnya",
                        style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Icon(
                        Icons.Default.Clear,
                        "",
                        modifier = Modifier.clickable{
                            onDismissRequest()
                        }.clip(CircleShape)
                    )
                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Text(
                    "Data Keamanan akan ${if(isDeleteMode.value == false) "diubah" else "Dihapus"}, Silahkan Masukan Kunci untuk Bisa Membuka Semua Data Pass di Grup Ini",
                    style = MaterialTheme.typography.subtitle1,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                if(passDataDetailsState.value.dataSecurity?.typeId == 2) {
                    Text(
                        passDataDetailsState.value.dataSecurity?.securityData ?: "",
                        style = MaterialTheme.typography.body1,
                        color = secondaryColor
                    )
                }
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                FormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = securityValue.value,
                    labelHints = if(passDataDetailsState.value.dataSecurity?.typeId == 1) "Masukan Password Anda" else "Masukan Jawaban Pertanyaan Di Atas",
                    isPassword = passDataDetailsState.value.dataSecurity?.typeId == 1,
                    leadingIcon = null,
                    onValueChange = {
                        securityValue.value = it
                    }
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        val formVerify = VerifySecurityDataGroupRequest(
                            securityData.value,
                            securityValue.value
                        )

                        groupDetailsViewModel.verifyPassForDecrypt(formVerify)
                    }
                ) {
                    when(passDataDetailsState.value.isLoading){
                        true -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 3.dp,
                                strokeCap = StrokeCap.Round
                            )
                        }
                        false -> {
                            Text(
                                text = "Verifikasi",
                                color = fontColor1,
                                style = MaterialTheme.typography.button.copy(fontSize = 14.sp)
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

fun proceedDeleteSecurityData(
    groupId: String,
    key : String,
    listPassDataEncrypted: List<GetPassDataGroup>,
    listUpdatePassDataToDecrypt: MutableList<UpdatePassDataGroupToDecrypt>,
    groupSettingsViewModel: GroupSettingsViewModel,
    securityDataId: Int
) {
    if(listPassDataEncrypted.isNotEmpty()){
        listPassDataEncrypted.forEach {
            val decData = CamelliaCrypto().decrypt(it.password,key)
            listUpdatePassDataToDecrypt.add(UpdatePassDataGroupToDecrypt(it.id,decData,false))
        }
        groupSettingsViewModel.sendDataPassToDecrypt(groupId, SendDataPassToDecrypt(listUpdatePassDataToDecrypt))
    } else {
        groupSettingsViewModel.deleteSecurityDataForGroup(
            groupId.toInt(),
            securityDataId
        )
    }
}
