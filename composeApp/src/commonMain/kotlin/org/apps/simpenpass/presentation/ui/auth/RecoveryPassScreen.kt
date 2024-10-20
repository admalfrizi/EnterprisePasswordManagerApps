package org.apps.simpenpass.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.style.authScreenBgColor
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.email_ic

@Composable
fun RecoveryPassScreen(
    navBack: () -> Unit,
    navToVerifyOtp: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var email by remember { mutableStateOf("") }
    var userFocus by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize().background(
            authScreenBgColor
        )
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
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

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Pemulihan Kata Sandi",
                style = MaterialTheme.typography.h1,
                fontSize = 32.sp
            )
            Spacer(
                modifier = Modifier.height(35.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Silahkan masukan email Anda, untuk pemulihan kata sandi. Yang akan mengirimkan link untuk pembuatan ulang kata sandi.",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Spacer(
                modifier = Modifier.height(95.dp)
            )
            CustomTextField(
                interactionSource = interactionSource,
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().onFocusChanged { focusState -> userFocus = focusState.isFocused },
                labelHints = "Masukan Email Anda",
                value = email,
                leadingIcon =  {
                    Icon(
                        painterResource(Res.drawable.email_ic),
                        tint = if(isFocused) Color(0xFF2060A8) else Color(0xFF384A92),
                        contentDescription = ""
                    )
                },
                isFocus = userFocus,
                onValueChange = {
                    email = it
                },
                focusColor = Color(0xFF4433DB)
            )
            Spacer(
                modifier = Modifier.height(63.dp)
            )
            Button(
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth().height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                   navToVerifyOtp()
                }){
                Text(
                    text = "Kirim Kode",
                    color = fontColor1,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}