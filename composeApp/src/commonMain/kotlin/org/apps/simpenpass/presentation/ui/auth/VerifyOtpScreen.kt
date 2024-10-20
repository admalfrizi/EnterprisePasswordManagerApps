package org.apps.simpenpass.presentation.ui.auth

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aakira.napier.Napier
import org.apps.simpenpass.style.authScreenBgColor
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.linkColor
import org.apps.simpenpass.style.secondaryColor

@Composable
fun VerifyOtpScreen(
    navBack: () -> Unit
) {
    var otp by remember { mutableStateOf("") }

    Napier.v("Data Otp : $otp")

    Box(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()
            .background(authScreenBgColor).imePadding()
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
                    text = "Silahkan Masukan OTP",
                style = MaterialTheme.typography.h1,
                fontSize = 32.sp
            )
            Spacer(
                modifier = Modifier.height(23.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Saat ini sedang mengirimkan kode OTP, untuk pemulihan kata sandi.",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Spacer(
                modifier = Modifier.height(21.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Silahkan untuk mengecek email yang masuk diperangkat anda.",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Spacer(
                modifier = Modifier.height(37.dp)
            )
            BasicTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                value = TextFieldValue(otp, selection = TextRange(otp.length)),
                onValueChange = { valueNum ->
                    if(valueNum.text.length <= 4) {
                        otp = valueNum.text
                    }
                },
                singleLine = true,
                cursorBrush = SolidColor(secondaryColor),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(4) { index ->
                            val otpData = when {
                                index >= otp.length -> ""
                                else -> otp[index].toString()
                            }
                            Card(
                                elevation = 0.dp,
                                modifier = Modifier.size(76.dp).weight(1f),
                                border = BorderStroke(width = 1.dp, color = Color.Transparent),
                                shape = RoundedCornerShape(13.dp),
                                backgroundColor = Color.White
                            ) {
                                Text(
                                    otpData,
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h2,
                                    color = secondaryColor
                                )
                            }
                            Spacer(modifier = Modifier.width(if(index != 3) 13.dp else 0.dp))
                        }
                    }
                }
            )
            Spacer(
                modifier = Modifier.height(58.dp)
            )
            Button(
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth().height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                shape = RoundedCornerShape(10.dp),
                onClick = {

                }){
                Text(
                    text = "Verifikasi Kode",
                    color = fontColor1,
                    style = MaterialTheme.typography.button
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Tidak Mendapatkan Kode ? Cek Di Spam",
                color = fontColor1,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                "Atau",
                color = fontColor1,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                "Kirimkan Kode Lain",
                color = linkColor,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

    }
}