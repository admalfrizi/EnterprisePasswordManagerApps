package org.apps.simpenpass.presentation.ui.create_data_pass.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.presentation.components.formComponents.BtnForm
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.formComponents.HeaderContainer
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.setToast
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FormScreen(
    navController: NavController,
    formViewModel: FormViewModel = koinViewModel(),
    passId: String
) {
    val isDismiss = remember { mutableStateOf(false) }
    val formState by formViewModel.formState.collectAsState()

    var nmAccount by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jnsPass by remember { mutableStateOf("") }
    var passData by remember { mutableStateOf("") }
    var urlPass by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    val formData = PassDataRequest(
        accountName = nmAccount,
        username = userName,
        desc = desc,
        email = email,
        jenisData = jnsPass,
        password = passData,
        url = urlPass,
    )

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

    LaunchedEffect(Unit){
        if(passId.isNotEmpty() && passId != "{passId}"){
            formViewModel.loadDataPassById(passId.toInt())
        }
    }

    if(formState.passData != null){
        userName = formState.passData?.username!!
        nmAccount = formState.passData?.accountName!!
        desc = formState.passData?.desc!!
        email = formState.passData?.email!!
        jnsPass = formState.passData?.jenisData ?: ""
        passData = formState.passData?.password!!
        urlPass = formState.passData?.url ?: ""
    }

//    Napier.v("Data Pass = $formData")

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        bottomBar = {
            BtnForm(
                {
                    if(passId.isNotEmpty() && passId != "{passId}"){
                        formViewModel.editUserPassData(passId = passId.toInt(), formData)
                    } else {
                        formViewModel.createUserPassData(formData)
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
                    }

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
                modifier = Modifier.padding(it).fillMaxSize().verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.Top
                ) {
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
                                if(formState.passData != null){
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
                                if(formState.passData != null){
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
                                if(formState.passData != null){
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
                                if(formState.passData != null){
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
                            value = passData,
                            isPassword = true,
                            labelHints = "Isi Data Password",
                            leadingIcon = null,
                            onValueChange = {
                                if(formState.passData != null){
                                    formState.passData?.password = it
                                }

                                passData = it
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
                                if(formState.passData != null){
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
                                if(formState.passData != null){
                                    formState.passData?.desc = it
                                }

                                desc = it
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(21.dp)
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Data Tambahan",
                            style = MaterialTheme.typography.body2,
                            color = secondaryColor
                        )
                        Spacer(
                            modifier = Modifier.height(9.dp)
                        )
                        Row {
                            Card(
                                modifier = Modifier.width(168.dp).weight(1f),
                                backgroundColor = Color(0xFF4470A9),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                ) {
                                    Text(
                                        "Nama Akun",
                                        style = MaterialTheme.typography.body1,
                                        color = fontColor1
                                    )
                                    Spacer(
                                        modifier = Modifier.height(26.dp)
                                    )
                                    Text(
                                        "Dari Grup Apa",
                                        style = MaterialTheme.typography.subtitle1,
                                        color = fontColor1,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier.width(7.dp)
                            )
                            Card(
                                modifier = Modifier.width(168.dp).weight(1f),
                                backgroundColor = Color(0xFF4470A9),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                ) {
                                    Text(
                                        "Nama Akun",
                                        style = MaterialTheme.typography.body1,
                                        color = fontColor1
                                    )
                                    Spacer(
                                        modifier = Modifier.height(26.dp)
                                    )
                                    Text(
                                        "Dari Grup Apa",
                                        style = MaterialTheme.typography.subtitle1,
                                        color = fontColor1,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )
                    }
                }
            }
        }
    )
}