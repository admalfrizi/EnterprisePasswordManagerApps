package org.apps.simpenpass.presentation.ui.create_data_pass.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.presentation.components.formComponents.BtnForm
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_option_ic

@Composable
fun FormPassGroupScreen(
    navController: NavController,
    bottomEdgeColor: MutableState<Color>,
    formPassGroupViewModel: FormPassGroupViewModel = koinViewModel()
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    val formPassGroupState by formPassGroupViewModel.formPassDataGroupState.collectAsStateWithLifecycle()

    var nmAccount by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jnsPass by remember { mutableStateOf("") }
    var passData by remember { mutableStateOf("") }
    var urlPass by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    bottomEdgeColor.value = secondaryColor

    ModalBottomSheetLayout(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        sheetState = sheetState,
        sheetContent = {
//            AddContentDataForm(
//                Modifier.fillMaxWidth(),
//                sheetState,
//                scope,
//                formViewModel,
//                nmData,
//                vlData
//            )
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
                        elevation = 0.dp
                    )
                },
                bottomBar = {
                    BtnForm(
                        {
                            if(formPassGroupState.passDataGroupId?.isNotEmpty() == true){
//                                formViewModel.editUserPassData(passId = passId.toInt(), formData)
                            } else {
//                                validatorData(
//                                    nmAccount,
//                                    passData,
//                                    formViewModel,
//                                    formData
//                                )
                            }
                        },
                        {
                            if(formPassGroupState.passData != null){
                                nmAccount = ""
                                userName = ""
                                desc = ""
                                email = ""
                                jnsPass = ""
                                passData = ""
                                urlPass = ""
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
                       formPassGroupViewModel,
                       it,
                       scope,
                       sheetState,
                       PassDataRequest(
                           nmAccount,
                           desc,
                           email,
                           jnsPass,
                           passData,
                           urlPass,
                           userName
                       )
                   )
                }
            )
        }
    )
}

@Composable
fun FormContentView(
    formPassGroupViewModel: FormPassGroupViewModel,
    paddingValues: PaddingValues,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    passDataRequest: PassDataRequest
) {
    val nmData = remember { mutableStateOf("") }
    val vlData = remember { mutableStateOf("") }

    val formState by formPassGroupViewModel.formPassDataGroupState.collectAsStateWithLifecycle()

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
                        value = passDataRequest.accountName ?: "",
                        labelHints = "Isi Nama Akun",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.accountName = it
//                                            }

                            passDataRequest.accountName = it
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
                        value = passDataRequest.username ?: "",
                        labelHints = "Isi Username",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.username = it
//                                            }
                            passDataRequest.username = it
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
                        value = passDataRequest.jenisData ?: "",
                        labelHints = "Isi Jenis Password",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.jenisData = it
//                                            }

                            passDataRequest.jenisData = it
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
                        value = passDataRequest.email ?: "",
                        labelHints = "Isi Email",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.email = it
//                                            }

                            passDataRequest.email = it
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
                        value = passDataRequest.password ?: "",
                        isPassword = true,
                        labelHints = "Isi Data Password",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.password = it
//                                            }

                            passDataRequest.password = it
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
                        value = passDataRequest.url ?: "",
                        labelHints = "Isi Data URL",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.url = it
//                                            }

                            passDataRequest.url = it
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
                        value = passDataRequest.desc ?: "",
                        labelHints = "Isi Catatan Berikut Ini",
                        leadingIcon = null,
                        onValueChange = {
//                                            if (formState.passData != null) {
//                                                formState.passData?.desc = it
//                                            }

                            passDataRequest.desc = it
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
//                AddContentPassView(
//                    formState,
//                    scope = scope,
//                    sheetState
//                )
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
            }
        }
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
            max = 0.dp
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
                        "",
                        style = MaterialTheme.typography.body1,
                        color = fontColor1
                    )
                    Spacer(
                        modifier = Modifier.height(26.dp)
                    )
                    Text(
                        "",
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
//                    scope.launch {
//                        sheetState.show()
//                    }
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