package org.apps.simpenpass.presentation.ui.auth

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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.presentation.components.DialogLoading
import org.apps.simpenpass.presentation.components.authComponents.DialogAuthEmpty
import org.apps.simpenpass.presentation.components.authComponents.DialogAuthWarning
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.authScreenBgColor
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.linkColor
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.isValidEmail
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.email_ic
import resources.user_password_auth

@Composable
fun AuthScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel(),
    bottomEdgeColor: MutableState<Color>
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isShowDialog = remember { mutableStateOf(false) }
    val isValidated = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val localCoroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }

    var emailFocus by remember { mutableStateOf(false) }
    var passwordFocus by remember { mutableStateOf(false) }
    val loginState by authViewModel.authState.collectAsState()

    bottomEdgeColor.value = secondaryColor

    fun validateForm(isShowDialog: MutableState<Boolean>,isValidated: MutableState<Boolean>){
        if(email.isEmpty() && password.isEmpty()){
            isShowDialog.value = true
        } else if(password.isEmpty()){
            isShowDialog.value = true
            msg = "Password anda Kosong !"
        } else if(!isValidEmail(email)){
            isShowDialog.value = true
            msg = "Format email anda tidak benar"
        } else {
            isValidated.value = true
            authViewModel.login(LoginRequest(email, password))
        }
    }

    if(!isValidated.value){
        if(isShowDialog.value){
            validateRes(email,password,isShowDialog,msg)
        }
    }

    if(loginState.isLoading){
        DialogLoading(onDismissRequest = { isShowDialog.value = false })
    }

    if(loginState.isLoggedIn){
        navHostController.navigate(Screen.Root.route){
            popUpTo(Screen.Auth.route){
                inclusive = true
            }
        }
        Napier.d("Response: ${loginState.token}")
    }

    if(loginState.error?.isNotEmpty() == true && !loginState.isLoading){
        localCoroutineScope.launch {
            snackBarHostState.showSnackbar(loginState.error!!)
        }
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).imePadding(),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        Box(
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()
                .background(authScreenBgColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    text = "Masukan Data untuk Login",
                    style = MaterialTheme.typography.h1,
                    fontSize = 32.sp
                )
                Spacer(
                    modifier = Modifier.height(90.dp)
                )
                //            if (routeCheck != null) {
                //                Text(
                //                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                //                    text = routeCheck,
                //                    style = MaterialTheme.typography.h1,
                //                    fontSize = 32.sp
                //                )
                //            }
                CustomTextField(
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .onFocusChanged { focusState -> emailFocus = focusState.isFocused },
                    labelHints = "Email",
                    value = email,
                    leadingIcon = {
                        Image(
                            painterResource(Res.drawable.email_ic),
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                        email = it
                    },
                    isFocus = emailFocus,
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(37.dp)
                )
                CustomTextField(
                    isPassword = true,
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .onFocusChanged { focusState -> passwordFocus = focusState.isFocused },
                    labelHints = "Password",
                    value = password,
                    isFocus = passwordFocus,
                    leadingIcon = {
                        Image(
                            painterResource(Res.drawable.user_password_auth),
                            contentDescription = "",
                        )
                    },
                    onValueChange = {
                        password = it
                    },
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(38.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable {
                        navHostController.navigate(Screen.SendOtp.route)
                    },
                    text = "Lupa Password ?",
                    color = fontColor1,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.End
                )
                Spacer(
                    modifier = Modifier.height(77.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Masuk Dengan Keamanan Biometrik",
                    color = linkColor,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        validateForm(isShowDialog, isValidated)
                    }
                ) {
                    Text(
                        text = "Login",
                        color = fontColor1,
                        style = MaterialTheme.typography.button
                    )
                }
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {
                        navHostController.navigate(Screen.Register.route)
                    },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Tidak Punya Akun ?",
                        color = fontColor1,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )
                    Text(
                        "Silahkan Daftar",
                        color = linkColor,
                        style = MaterialTheme.typography.subtitle1
                    )
                }

            }
        }
    }
}

@Composable
fun validateRes(email: String, password: String, isShowDialog: MutableState<Boolean>, msg: String) {
    if (email.isEmpty() && password.isEmpty()) {
        DialogAuthEmpty(onDismissRequest = { isShowDialog.value = false })
    } else  {
        DialogAuthWarning(
            onDismissRequest = { isShowDialog.value = false },
            warnTitle = msg,
        )
    }
}