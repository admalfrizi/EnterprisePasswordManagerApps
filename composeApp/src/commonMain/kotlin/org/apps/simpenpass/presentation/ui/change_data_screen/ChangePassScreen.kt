package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.primaryColor
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.pass_ic
import resources.user_password_auth

@Composable
fun ChangePassScreen(
    token: String,
    navToBack : () -> Unit,
    navToHome : () -> Unit,
    changeDataViewModel: ChangeDataViewModel = koinViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    var newPassword by remember { mutableStateOf("") }
    var cPassword by remember { mutableStateOf("") }
    val isValidated = remember { mutableStateOf(false) }
    val changeDataState by changeDataViewModel.changeDataState.collectAsState()
    val isDismiss = remember { mutableStateOf(true) }
    var isPopUp by remember { mutableStateOf(false) }

    if(changeDataState.isLoading){
        popUpLoading(isDismiss)
    }

    if(changeDataState.isSuccess){
        isPopUp = true
    }

    if(isPopUp){
        SuccessResetPass(
            {
                isPopUp = false
            },
            {
                navToHome()
                isPopUp = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).imePadding().fillMaxSize(),
        backgroundColor = secondaryColor,
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                title = {

                },
                elevation = 0.dp,
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
            modifier = Modifier.fillMaxWidth().imePadding()
        ){
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
                    validateData(
                        newPassword,
                        cPassword,
                        token,
                        isValidated,
                        changeDataViewModel
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

@Composable
fun SuccessResetPass(
    onDismiss : () -> Unit,
    onClick : () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                "Password Anda Telah Sukses di Perbaharui",
                style = MaterialTheme.typography.h6,
                color = primaryColor
            )
        },
        text = {
            Text(
                "Silahkan untuk Kembali Ke Halaman Utama atau Login Kembali",
                style = MaterialTheme.typography.subtitle1,
                color = primaryColor
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 24.dp, bottom = 19.dp),
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    "Ok",
                    modifier = Modifier.clickable { onClick() },
                    style = MaterialTheme.typography.subtitle2,
                    color = primaryColor
                )
            }

        },
    )
}

fun validateData(
    newPass: String,
    cPass: String,
    token: String,
    isValidated: MutableState<Boolean>,
    changeDataViewModel: ChangeDataViewModel
) {
    if (newPass.isEmpty() && cPass.isEmpty()) {
        setToast("Data password tidak boleh kosong")
    } else if (cPass.isEmpty()) {
        setToast("Konfirmasi Password harus Di Isi !")
    } else if(newPass != cPass){
        setToast("Masukan Data Password yang Valid")
    } else {
        isValidated.value = true
        changeDataViewModel.resetPassword(password = newPass, token = token)
    }
}