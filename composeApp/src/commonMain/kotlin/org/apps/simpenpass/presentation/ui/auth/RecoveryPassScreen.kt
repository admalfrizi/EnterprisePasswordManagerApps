package org.apps.simpenpass.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.style.authScreenBgColor
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.pass_ic
import resources.user_password_auth

@Composable
fun RecoveryPassScreen(
    token: String,
    navBack : () -> Unit,
    navToLogin : () -> Unit,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    var newPassword by remember { mutableStateOf("") }
    var cPassword by remember { mutableStateOf("") }
    val isValidated = remember { mutableStateOf(false) }
    val authState by authViewModel.authState.collectAsState()
    val isDismiss = remember { mutableStateOf(true) }

    if(authState.isLoading){
        popUpLoading(isDismiss)
    }

    if(authState.isResetPass){
        navToLogin()
        setToast("Password Berhasil DiReset !")
    }

    Scaffold(
        modifier = Modifier.imePadding()
    ) {
        Box(
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize().background(
                authScreenBgColor
            ).verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        navBack()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.height(37.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    text = "Silahkan Reset Password",
                    style = MaterialTheme.typography.h1,
                    fontSize = 32.sp
                )
                Spacer(
                    modifier = Modifier.height(35.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    text = "Masukan password baru untuk melakukan pembaharuan password pada akun anda",
                    style = MaterialTheme.typography.caption,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier.height(57.dp)
                )
                CustomTextField(
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    labelHints = "Masukan Password Baru",
                    value = newPassword,
                    leadingIcon =  {
                        Icon(
                            painterResource(Res.drawable.user_password_auth),
                            tint = Color(0xFF384A92),
                            contentDescription = ""
                        )
                    },
                    isFocus = false,
                    isPassword = true,
                    onValueChange = {
                        newPassword = it
                    },
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(37.dp)
                )
                CustomTextField(
                    interactionSource = interactionSource,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    labelHints = "Konfirmasi Password Baru",
                    value = cPassword,
                    leadingIcon =  {
                        Icon(
                            painterResource(Res.drawable.pass_ic),
                            tint = Color(0xFF384A92),
                            contentDescription = ""
                        )
                    },
                    isFocus = false,
                    onValueChange = {
                        cPassword = it
                    },
                    isPassword = true,
                    focusColor = Color(0xFF4433DB)
                )
                Spacer(
                    modifier = Modifier.height(52.dp)
                )
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        validateCheckData(
                            newPass = newPassword,
                            cPass = cPassword,
                            token = token,
                            isValidated = isValidated,
                            authViewModel = authViewModel,
                        )
                    }
                ){
                    Text(
                        text = "Reset Password",
                        color = fontColor1,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

fun validateCheckData(
    newPass: String,
    cPass: String,
    token: String,
    isValidated: MutableState<Boolean>,
    authViewModel: AuthViewModel
) {
    if (newPass.isEmpty() && cPass.isEmpty()) {
        setToast("Data password tidak boleh kosong")
    } else if (cPass.isEmpty()) {
        setToast("Konfirmasi Password harus DiIsi !")
    } else if(newPass != cPass){
        setToast("Masukan Data Password yang Valid")
    } else {
        isValidated.value = true
        authViewModel.resetPassword(password = newPass, token = token)
    }
}