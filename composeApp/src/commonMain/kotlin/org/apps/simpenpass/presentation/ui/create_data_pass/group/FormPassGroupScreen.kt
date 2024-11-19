package org.apps.simpenpass.presentation.ui.create_data_pass.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataGroupRequest
import org.apps.simpenpass.presentation.components.formComponents.BtnForm
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_option_ic
import resources.menu_ic
import kotlin.String

@Composable
fun FormPassGroupScreen(
    navController: NavController,
    bottomEdgeColor: MutableState<Color>,
    formPassGroupViewModel: FormPassGroupViewModel = koinViewModel()
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    val isDismiss = remember { mutableStateOf(false) }
    val formPassGroupState by formPassGroupViewModel.formPassDataGroupState.collectAsStateWithLifecycle()
    var isDropdownShow by remember { mutableStateOf(false) }
    var isDialogPopup by remember { mutableStateOf(false) }

    var nmAccount = remember { mutableStateOf("") }
    var userName = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var jnsPass = remember { mutableStateOf("") }
    var passData = remember { mutableStateOf("") }
    var urlPass = remember { mutableStateOf("") }
    var desc = remember { mutableStateOf("") }
    val nmData = remember { mutableStateOf("") }
    val vlData = remember { mutableStateOf("") }
    var roleId = remember { mutableStateOf(-1) }

    bottomEdgeColor.value = secondaryColor

    if(sheetState.isVisible){
        bottomEdgeColor.value = Color.White
    }

    if(formPassGroupState.isCreated){
        navController.navigateUp()
        setToast("Data Berhasil Ditambahkan")
    }

    if(sheetState.isVisible){
        nmData.value = ""
        vlData.value = ""
    }

    if(formPassGroupState.isLoading){
        popUpLoading(isDismiss)
    }

    if(formPassGroupState.isCreated){
        navController.navigateUp()
        setToast("Data Berhasil Ditambahkan")
    }

    if(formPassGroupState.isUpdated) {
        navController.navigateUp()
        setToast("Data Berhasil Diperbarui")
    }

    if(isDialogPopup){
        DialogEditRoleInPassData(
            roleId,
            formPassGroupState,
            formPassGroupState.listRoleData
        ) {
            isDialogPopup = false
        }
    }

    if(formPassGroupState.passData != null){
        userName.value = formPassGroupState.passData?.username!!
        nmAccount.value = formPassGroupState.passData?.accountName!!
        desc.value = formPassGroupState.passData?.desc!!
        email.value = formPassGroupState.passData?.email ?: ""
        jnsPass.value = formPassGroupState.passData?.jenisData ?: ""
        passData.value = formPassGroupState.passData?.password!!
        urlPass.value = formPassGroupState.passData?.url ?: ""
    }

    if(formPassGroupState.passData?.posisiId != null){
        roleId.value = formPassGroupState.passData?.posisiId!!
    }

    LaunchedEffect(formPassGroupState.groupId != "{groupId}"){
        if(formPassGroupState.groupId != null && formPassGroupState.groupId != "{groupId}"){
            formPassGroupViewModel.getListRoleData(formPassGroupState.groupId!!)
        }
    }

    val formData = PassDataGroupRequest(
        accountName = nmAccount.value,
        username = userName.value,
        desc = desc.value,
        email = email.value,
        jenisData = jnsPass.value,
        password = passData.value,
        url = urlPass.value,
        posisiId = roleId.value,
        addPassContent = formPassGroupState.insertAddContentPassData
    )

    Napier.v("formGroup :$formData")
    Napier.v("roleId : ${roleId.value}")

    ModalBottomSheetLayout(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        sheetState = sheetState,
        sheetContent = {
            AddContentPassDataGroupForm(
                Modifier.fillMaxWidth(),
                sheetState,
                scope,
                formPassGroupViewModel,
                nmData,
                vlData
            )
        },
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = Color.White,
        content = {
            Scaffold(
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                topBar = {
                    TopAppBar(
                        backgroundColor = secondaryColor,
                        title = {
                            Text(
                                "Buat Data Pass Grup",
                                style = MaterialTheme.typography.h6,
                                color = fontColor1
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navController.navigateUp()
//                                    groupState.clearState()
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
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                }
                            )
                            DropdownMenu(
                                expanded = isDropdownShow,
                                onDismissRequest = { isDropdownShow = false }
                            ) {
                                DropdownMenuItem(
                                    content = {
                                        Text(text = "Edit Role Pass Group")
                                    },
                                    onClick = {
//                                        navController.navigateUp()
                                        isDialogPopup = true
                                        isDropdownShow = false
                                    }
                                )
                            }
                        },
                        elevation = 0.dp
                    )
                },
                bottomBar = {
                    BtnForm(
                        {
                            if(formPassGroupState.passDataGroupId != "-1"){
                                formPassGroupViewModel.updatePassData(formPassGroupState.groupId!!,formPassGroupState.passDataGroupId!!, formData)
                            } else {
                                validatorPassData(
                                    nmAccount.value,
                                    passData.value,
                                    formPassGroupViewModel,
                                    formData,
                                    formPassGroupState.groupId!!
                                )
                            }
                        },
                        {
                            if(formPassGroupState.passData != null){
                                nmAccount.value = ""
                                userName.value = ""
                                desc.value = ""
                                email.value = ""
                                jnsPass.value = ""
                                passData.value = ""
                                urlPass.value = ""
                            }
//                            formPassGroupViewModel.resetValue()
                            navController.navigateUp()
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(secondaryColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                        isPassIdExist = false
                    )
                },
                content = {
                   FormContentView(
                       formPassGroupState,
                       it,
                       scope,
                       sheetState,
                       nmAccount ,
                        desc ,
                        email ,
                        jnsPass ,
                        passData ,
                        urlPass ,
                        userName
                   )
                }
            )
        }
    )
}

@Composable
fun FormContentView(
    formState: FormPassGroupState,
    paddingValues: PaddingValues,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    nmAccount : MutableState<String>,
    desc : MutableState<String>,
    email : MutableState<String>,
    jnsPass : MutableState<String>,
    passData : MutableState<String>,
    urlPass : MutableState<String>,
    userName : MutableState<String>
) {
    Box(
        modifier = Modifier.padding(paddingValues).fillMaxWidth().wrapContentHeight()
    ) {
        LazyColumn(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Nama Akun",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = nmAccount.value,
                        labelHints = "Isi Nama Akun",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.accountName = it
                            }

                            nmAccount.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Username",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userName.value,
                        labelHints = "Isi Username",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.username = it
                            }
                           userName.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Jenis Password",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = jnsPass.value,
                        labelHints = "Isi Jenis Password",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.jenisData = it
                            }

                            jnsPass.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Email",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email.value,
                        labelHints = "Isi Email",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.email = it
                            }

                            email.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Password",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = passData.value,
                        isPassword = true,
                        labelHints = "Isi Data Password",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.password = it
                            }

                            passData.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "URL Website",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = urlPass.value,
                        labelHints = "Isi Data URL",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.url = it
                            }

                            urlPass.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Catatan/Deskripsi",
                        style = MaterialTheme.typography.body2,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(9.dp)
                    )
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = desc.value,
                        labelHints = "Isi Catatan Berikut Ini",
                        leadingIcon = null,
                        onValueChange = {
                            if (formState.passData != null) {
                                formState.passData.desc = it
                            }

                            desc.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(21.dp)
                    )
                }
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    text = "Data Tambahan",
                    style = MaterialTheme.typography.body2,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(9.dp)
                )
                AddContentPassView(
                    formState,
                    scope = scope,
                    sheetState
                )
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
            }
        }
    }
}

@Composable
fun AddContentPassDataGroupForm(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    formViewModel: FormPassGroupViewModel,
    nmData: MutableState<String>,
    vlData: MutableState<String>
) {
    Column(
        modifier = modifier,
    ){
        Spacer(
            modifier = Modifier.height(16.dp)
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
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            text = "Data Tambahan",
            style = MaterialTheme.typography.button.copy(
                color = secondaryColor
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            text = "Nama Data",
            style = MaterialTheme.typography.body2.copy(
                color = secondaryColor
            )
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = nmData.value,
            labelHints = "Isi Jenis Nama Data",
            leadingIcon = null,
            onValueChange = {
                nmData.value = it
            }
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            text = "Isi Data",
            style = MaterialTheme.typography.body2.copy(
                color = secondaryColor
            )
        )
        Spacer(
            modifier = Modifier.height(9.dp)
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = vlData.value,
            labelHints = "Isi Data Tambahan",
            leadingIcon = null,
            onValueChange = {
                vlData.value = it
            }
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = {
                scope.launch {
                    formViewModel.addContentDataToList(InsertAddContentDataPass(nmData.value,vlData.value))
                    sheetState.hide()
                }

            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(btnColor),
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

@Composable
fun AddContentPassView(
    formState: FormPassGroupState,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).heightIn(
            max = checkData(formState)
        ),
        userScrollEnabled = false
    ){
        items(formState.listAddContentPassData){ item ->
            Card(
                modifier = Modifier.width(168.dp),
                backgroundColor = Color(0xFF4470A9),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                ) {
                    Text(
                        item.nmData,
                        style = MaterialTheme.typography.body1,
                        color = fontColor1
                    )
                    Spacer(
                        modifier = Modifier.height(26.dp)
                    )
                    Text(
                        item.vlData,
                        style = MaterialTheme.typography.subtitle1,
                        color = fontColor1,
                        fontSize = 10.sp
                    )
                }
            }
        }
        items(formState.insertAddContentPassData){ item ->
            Card(
                modifier = Modifier.width(168.dp),
                backgroundColor = Color(0xFF4470A9),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                ) {
                    Text(
                        item.nmData,
                        style = MaterialTheme.typography.body1,
                        color = fontColor1
                    )
                    Spacer(
                        modifier = Modifier.height(26.dp)
                    )
                    Text(
                        item.vlData,
                        style = MaterialTheme.typography.subtitle1,
                        color = fontColor1,
                        fontSize = 10.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.width(168.dp).clickable {
                    scope.launch {
                        sheetState.show()
                    }
                },
                backgroundColor = Color(0xFF78A1D7),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(18.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Buat Data Baru",
                        style = MaterialTheme.typography.body1,
                        color = fontColor1,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Card(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = Color(0xFF6C8BB4),
                        elevation = 0.dp,
                        content = {
                            Image(
                                painterResource(Res.drawable.add_option_ic),""
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DialogEditRoleInPassData(
    roleId: MutableState<Int>,
    formState: FormPassGroupState,
    listRoleData: List<RoleGroupData>,
    onDismissDialog: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ){
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                ) {
                    Text(
                        "Masukan Data Role",
                        style = MaterialTheme.typography.body1,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    IconButton(
                        onClick = {
                            onDismissDialog()
                        },
                        content = {
                            Image(
                                Icons.Default.Clear,
                                ""
                            )
                        }
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    items(listRoleData){ item ->
                        Box(
                            modifier = Modifier.fillMaxWidth().background(Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    item.nmPosisi,
                                    style = MaterialTheme.typography.caption,
                                    color = secondaryColor
                                )
                                Checkbox(
                                    onCheckedChange = {
                                        if(formState.passData != null){
                                            formState.passData.posisiId = item.id
                                        }
                                        roleId.value = item.id
                                    },
                                    checked = roleId.value == item.id,
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

fun checkData(
    formState: FormPassGroupState,
): Dp {
    val totalSize = (formState.listAddContentPassData.size + formState.insertAddContentPassData.size)

    return if (formState.listAddContentPassData.isEmpty() && formState.insertAddContentPassData.isEmpty()) {
        105.dp
    } else if(totalSize.toString().isNotEmpty()) {
        (totalSize * 105).dp
    }  else {
        0.dp
    }
}

fun validatorPassData(
    accountName: String,
    pass: String,
    formViewModel: FormPassGroupViewModel,
    formData: PassDataGroupRequest,
    groupId: String
) {
    if(accountName.isEmpty() && pass.isEmpty()){
        setToast("Nama Akun dan Password Tidak Boleh Kosong")
    } else {
        formViewModel.createPassData(formData,groupId)
    }
}