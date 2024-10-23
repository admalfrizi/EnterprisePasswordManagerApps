package org.apps.simpenpass.presentation.ui.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.presentation.components.DialogLoading
import org.apps.simpenpass.presentation.components.authComponents.DialogRegisterEmpty
import org.apps.simpenpass.presentation.components.authComponents.DialogRegisterWarning
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.authScreenBgColor
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.linkColor
import org.apps.simpenpass.utils.isValidEmail
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.email_ic
import resources.pass_ic
import resources.user_ic
import resources.user_password_auth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel()
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val registerState by authViewModel.authState.collectAsState()

    var msg by remember { mutableStateOf("") }

    val isShowDialog = remember { mutableStateOf(false) }
    val isValidated = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var userFocus by remember { mutableStateOf(false) }
    var emailFocus by remember { mutableStateOf(false) }
    var passwordFocus by remember { mutableStateOf(false) }
    var cpassFocus by remember { mutableStateOf(false) }

    fun validateForm() {
        if (user.isEmpty() && email.isEmpty() && password.isEmpty()) {
            isShowDialog.value = true
        } else if (password.isEmpty() && cpassword.isEmpty()) {
            isShowDialog.value = true
            msg = "Password anda tidak boleh kosong !"
        } else if(cpassword.isEmpty()){
            isShowDialog.value = true
            msg = "Tolong Konfirmasikan Password Anda !"
        } else if(!isValidEmail(email)) {
            isShowDialog.value = true
            msg = "Format email anda tidak benar !"
        } else if(cpassword != password){
            isShowDialog.value = true
            msg = "Password anda tidak benar !"
        } else {
            isValidated.value = true
            authViewModel.register(RegisterRequest(user, email, password))
        }
    }

    if(!isValidated.value){
        if(isShowDialog.value){
            validateReg(email,password,isShowDialog,msg)
        }
    }

    if(registerState.isLoading){
        DialogLoading(onDismissRequest = {  })
    }

    if(registerState.isRegistered){
        navHostController.navigate(Screen.Login.route)
        coroutineScope.launch {
            snackBarHostState.showSnackbar("Berhasil Register")
        }
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }


    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).imePadding(),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        Box(
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()
                .background(
                    authScreenBgColor
                ).imePadding()
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    text = "Silahkan Mengisi Data Diri untuk Bisa Menggunakan Aplikasi ini",
                    style = MaterialTheme.typography.h1,
                    fontSize = 32.sp
                )
                Spacer(
                    modifier = Modifier.height(42.dp)
                )
                CustomTextField(
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .onFocusChanged { focusState -> userFocus = focusState.isFocused },
                    labelHints = "Nama",
                    value = user,
                    leadingIcon = {
                        Icon(
                            painterResource(Res.drawable.user_ic),
                            tint = if (isFocused) Color(0xFF2060A8) else Color(0xFF384A92),
                            contentDescription = ""
                        )
                    },
                    isFocus = userFocus,
                    onValueChange = {
                        user = it
                    },
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(21.dp)
                )
                CustomTextField(
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .onFocusChanged { focusState -> emailFocus = focusState.isFocused },
                    labelHints = "Email",
                    value = email,
                    leadingIcon = {
                        Icon(
                            painterResource(Res.drawable.email_ic),
                            tint = if (isFocused) Color(0xFF2060A8) else Color(0xFF384A92),
                            contentDescription = ""
                        )
                    },
                    isFocus = emailFocus,
                    onValueChange = {
                        email = it
                    },
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(21.dp)
                )
                CustomTextField(
                    isPassword = true,
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .onFocusChanged { focusState -> passwordFocus = focusState.isFocused },
                    labelHints = "Password",
                    value = password,
                    leadingIcon = {
                        Image(
                            painterResource(Res.drawable.user_password_auth),
                            contentDescription = ""
                        )
                    },
                    isFocus = passwordFocus,
                    onValueChange = {
                        password = it
                    },
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(21.dp)
                )
                CustomTextField(
                    isPassword = true,
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .onFocusChanged { focusState -> cpassFocus = focusState.isFocused }
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                    labelHints = "Konfirmasi Password",
                    value = cpassword,
                    leadingIcon = {
                        Icon(
                            painterResource(Res.drawable.pass_ic),
                            tint = if (isFocused) Color(0xFF2060A8) else Color(0xFF384A92),
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                        cpassword = it
                    },
                    isFocus = cpassFocus,
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(42.dp)
                )
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { validateForm() }) {
                    Text(
                        text = "Daftarkan",
                        color = fontColor1,
                        style = MaterialTheme.typography.button
                    )
                }
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {
                        navHostController.navigate(Screen.Login.route)
                    },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Sudah Punya Akun ?",
                        color = fontColor1,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )
                    Text(
                        "Silahkan Login",
                        color = linkColor,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}


@Composable
fun validateReg(email: String, password: String, isShowDialog: MutableState<Boolean>, msg: String) {
    if (email.isEmpty() && password.isEmpty()) {
        DialogRegisterEmpty(onDismissRequest = { isShowDialog.value = false })
    } else  {
        DialogRegisterWarning(
            onDismissRequest = { isShowDialog.value = false },
            warnTitle = msg,
        )
    }
}