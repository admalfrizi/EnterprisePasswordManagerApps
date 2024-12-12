package org.apps.simpenpass.presentation.ui.create_data_pass.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.FormAddContentPassData
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.presentation.components.formComponents.BtnForm
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.formComponents.HeaderContainer
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.CamelliaCrypto
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_option_ic

@Composable
fun FormScreen(
    bottomEdgeColor: MutableState<Color>,
    navController: NavController,
    formViewModel: FormViewModel = koinViewModel(),
    passId: String
) {
    val isDismiss = remember { mutableStateOf(false) }
    val formState by formViewModel.formState.collectAsState()
    val key = "An13sPr@b0w0G@nj@rG1br@n1m1n"
    var encData by remember { mutableStateOf("") }
    var dec by remember { mutableStateOf("") }

    var nmAccount by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jnsPass by remember { mutableStateOf("") }
    var passData by remember { mutableStateOf("") }
    var urlPass by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    var isInsertData = remember { mutableStateOf(false) }
    val addContentId = remember { mutableStateOf(0) }
    val nmData = remember { mutableStateOf("") }
    val vlData = remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    val selectedDelete = remember {
        mutableStateListOf<FormAddContentPassData>()
    }
    val updateListItemAddContent = remember {
        mutableStateListOf<FormAddContentPassData>()
    }
    val listAddContentPassData = remember {
        mutableStateListOf<FormAddContentPassData>()
    }
    val insertAddContentDataPass = remember {
        mutableStateListOf<InsertAddContentDataPass>()
    }

    if(passData.isNotEmpty() && passData.length >= 4){
        val enc = CamelliaCrypto().encrypt(passData,key)
        encData = enc
        Napier.v("Encrypt : ${encData.replace(" ","")}")
    }

    bottomEdgeColor.value = secondaryColor

    if(sheetState.isVisible){
        bottomEdgeColor.value = Color.White
    }

    if(formState.isLoading){
        popUpLoading(isDismiss)
    }

    if(formState.isCreated){
        navController.navigateUp()
        setToast("Data Berhasil Ditambahkan")
    }

    if(formState.isUpdated) {
        navController.navigateUp()
        setToast("Data Berhasil Diperbarui")
    }

    LaunchedEffect(passId){
        if(passId.isNotEmpty() && passId != "{passId}"){
            formViewModel.loadDataPassById(passId.toInt())
        }
    }

    LaunchedEffect(formState.listAddContentPassData.isNotEmpty()){
        if(formState.listAddContentPassData.isNotEmpty()){
            formState.listAddContentPassData.forEach {
                listAddContentPassData.add(FormAddContentPassData(it.id,it.nmData,it.vlData))
            }
        }
    }

    if(formState.passData != null){
        userName = formState.passData?.username!!
        nmAccount = formState.passData?.accountName!!
        desc = formState.passData?.desc!!
        email = formState.passData?.email!!
        jnsPass = formState.passData?.jenisData ?: ""
        urlPass = formState.passData?.url ?: ""

        dec = CamelliaCrypto().decrypt(formState.passData?.password!!,key)
    }


//    Napier.v("Decrypt : $dec")

    ModalBottomSheetLayout(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        sheetState = sheetState,
        sheetContent = {
            AddContentDataForm(
                Modifier.fillMaxWidth(),
                sheetState,
                insertAddContentDataPass,
                updateListItemAddContent,
                listAddContentPassData,
                scope,
                isInsertData,
                nmData,
                vlData,
                addContentId
            )
        },
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = Color.White,
        content = {
            Scaffold(
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                bottomBar = {
                    BtnForm(
                        {
                            if(dec.isNotEmpty()){
                                encData = CamelliaCrypto().encrypt(dec,key)
                            }

                            val formData = PassDataRequest(
                                accountName = nmAccount,
                                username = userName,
                                desc = desc,
                                email = email,
                                jenisData = jnsPass,
                                password = encData,
                                url = urlPass,
                            )

                            if(passId.isNotEmpty() && passId != "{passId}"){
                                formViewModel.editUserPassData(
                                    passId = passId.toInt(),
                                    formData,
                                    insertAddContentDataPass,
                                    selectedDelete,
                                    updateListItemAddContent
                                )
                            } else {
                                validatorData(
                                    nmAccount,
                                    insertAddContentDataPass,
                                    passData,
                                    formViewModel,
                                    formData
                                )
                            }
                        },
                        {
                            if(formState.passData != null){
                                nmAccount = ""
                                userName = ""
                                desc = ""
                                email = ""
                                jnsPass = ""
                                passData = ""
                                urlPass = ""
                                encData = ""
                                dec = ""
                            }
                            formViewModel.resetValue()
                            navController.navigateUp()
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(secondaryColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                        isPassIdExist = passId.isNotEmpty() && passId != "{passId}"
                    )
                },
                content = {
                    Box(
                        modifier = Modifier.padding(it).fillMaxWidth().wrapContentHeight()
                    ) {
                        LazyColumn(
                            modifier = Modifier.align(Alignment.TopCenter),
                            verticalArrangement = Arrangement.Top
                        ) {
                            item {
                                HeaderContainer()
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
                                        value = nmAccount,
                                        labelHints = "Isi Nama Akun",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                formState.passData?.accountName = it
                                            }

                                            nmAccount = it
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
                                        value = userName,
                                        labelHints = "Isi Username",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                formState.passData?.username = it
                                            }
                                            userName = it
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
                                        value = jnsPass,
                                        labelHints = "Isi Jenis Password",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                formState.passData?.jenisData = it
                                            }

                                            jnsPass = it
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
                                        value = email,
                                        labelHints = "Isi Email",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                formState.passData?.email = it
                                            }

                                            email = it
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
                                        value = if(dec.isNotEmpty()) dec else passData,
                                        isPassword = true,
                                        labelHints = "Isi Data Password",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                dec = it
                                            } else {
                                                passData = it
                                            }
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
                                        value = urlPass,
                                        labelHints = "Isi Data URL",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                formState.passData?.url = it
                                            }

                                            urlPass = it
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
                                        value = desc,
                                        labelHints = "Isi Catatan Berikut Ini",
                                        leadingIcon = null,
                                        onValueChange = {
                                            if (formState.passData != null) {
                                                formState.passData?.desc = it
                                            }

                                            desc = it
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
                                AddContentPassDataUserView(
                                    { data ->
                                        scope.launch {
                                            sheetState.show()
                                        }

                                        addContentId.value = data.id
                                        nmData.value = data.nmData
                                        vlData.value = data.vlData
                                    },
                                    { data ->
                                        scope.launch {
                                            sheetState.show()
                                        }
                                        isInsertData.value = true
                                        addContentId.value = data.id
                                        nmData.value = data.nmData
                                        vlData.value = data.vlData
                                    },
                                    listAddContentPassData,
                                    selectedDelete,
                                    insertAddContentDataPass,
                                    formState,
                                    sheetState,
                                    scope
                                )
                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun AddContentPassDataUserView(
    updateAddContentPass: (FormAddContentPassData) -> Unit,
    updateInsertAddContentPass: (InsertAddContentDataPass) -> Unit,
    listAddContentPassData: MutableList<FormAddContentPassData>,
    selectedDelete: MutableList<FormAddContentPassData>,
    insertAddContentDataPass: MutableList<InsertAddContentDataPass>,
    formState: FormState,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).heightIn(
            max = checkData(formState,insertAddContentDataPass)
        ),
        userScrollEnabled = false
    ){
        items(listAddContentPassData){ items ->
            Card(
                modifier = Modifier.width(168.dp).clickable {
                    updateAddContentPass(items)
                },
                backgroundColor = Color(0xFF4470A9),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            items.nmData,
                            style = MaterialTheme.typography.body1,
                            color = fontColor1,
                            modifier = Modifier.weight(1f),
                            maxLines = 2,
                            minLines = 2,
                        )
                        Spacer(
                            modifier = Modifier.width(24.dp)
                        )
                        Icon(
                            Icons.Default.Clear,
                            "",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                selectedDelete.add(FormAddContentPassData(items.id,items.nmData,items.vlData))
                                if(selectedDelete.contains(FormAddContentPassData(items.id,items.nmData,items.vlData))){
                                    listAddContentPassData.remove(items)
                                }
                            }
                        )
                    }


                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                    Text(
                        items.vlData,
                        style = MaterialTheme.typography.subtitle1,
                        color = fontColor1,
                        fontSize = 10.sp
                    )
                }
            }
        }

        items(insertAddContentDataPass){ items ->
            Card(
                modifier = Modifier.width(168.dp).clickable {
                    updateInsertAddContentPass(items)
                },
                backgroundColor = Color(0xFF4470A9),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            items.nmData,
                            style = MaterialTheme.typography.body1,
                            color = fontColor1,
                            modifier = Modifier.weight(1f),
                            maxLines = 2,
                            minLines = 2,
                        )
                        Icon(
                            Icons.Default.Clear,
                            "",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                insertAddContentDataPass.remove(items)
                            }
                        )
                    }
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                    Text(
                        items.vlData,
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
fun AddContentDataForm(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    insertAddContentDataPass: MutableList<InsertAddContentDataPass>,
    updateListAddData: MutableList<FormAddContentPassData>,
    listAddContentPassData: MutableList<FormAddContentPassData>,
    scope: CoroutineScope,
    isInsertData: MutableState<Boolean>,
    nmData: MutableState<String>,
    vlData: MutableState<String>,
    addContentId: MutableState<Int>
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
    ){
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        IconButton(
            onClick = {
                focusManager.clearFocus()
                addContentId.value = 0
                nmData.value = ""
                vlData.value = ""
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).focusRequester(focusRequester),
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).focusRequester(focusRequester),
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
                focusManager.clearFocus()
                when(addContentId.value != 0){
                    true -> {
                        if(!isInsertData.value){
                            val index = listAddContentPassData.indexOfFirst { it.id == addContentId.value }
                            if (index != -1) {
                                listAddContentPassData[index] = listAddContentPassData[index].copy(nmData = nmData.value, vlData = vlData.value)
                                updateListAddData.add(listAddContentPassData[index])
                            }
                        } else {
                            val index = insertAddContentDataPass.indexOfFirst { it.id == addContentId.value }
                            if (index != -1) {
                                insertAddContentDataPass[index] = insertAddContentDataPass[index].copy(nmData = nmData.value, vlData = vlData.value)
                            }
                        }

                        scope.launch {
                            sheetState.hide()
                        }

                        isInsertData.value = false
                        nmData.value = ""
                        vlData.value = ""
                        addContentId.value = 0
                    }
                    false -> {
                        insertAddContentDataPass.add(InsertAddContentDataPass(insertAddContentDataPass.size + 1,nmData.value,vlData.value))
                        scope.launch {
                            sheetState.hide()
                        }
                        nmData.value = ""
                        vlData.value = ""
                    }
                }


            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(btnColor),
            content = {
                Text(
                    if(addContentId.value != 0) "Ubah Data" else "Tambahkan",
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

fun checkData(
    formState: FormState,
    insertAddContentDataPass: MutableList<InsertAddContentDataPass>
): Dp {
    val totalSize = (formState.listAddContentPassData.size + insertAddContentDataPass.size)

    return if (formState.listAddContentPassData.isEmpty() && insertAddContentDataPass.isEmpty()) {
        120.dp
    } else if(totalSize.toString().isNotEmpty()) {
        (totalSize * 120).dp
    }  else {
        0.dp
    }
}

fun validatorData(
    accountName: String,
    insertAddContentDataPass: MutableList<InsertAddContentDataPass>,
    pass: String,
    formViewModel: FormViewModel,
    formData: PassDataRequest,
) {
    if(accountName.isEmpty() && pass.isEmpty()){
        setToast("Nama Akun dan Password Tidak Boleh Kosong")
    }  else {
        formViewModel.createUserPassData(formData,insertAddContentDataPass)
    }
}