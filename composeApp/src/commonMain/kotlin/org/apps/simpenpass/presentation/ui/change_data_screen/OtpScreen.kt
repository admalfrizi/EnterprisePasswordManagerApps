package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.ssp
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.linkColor
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.otp_icon

@Composable
fun OtpScreen(
    navToBack: () -> Unit,
    navToChangePass: (String) -> Unit,
    navToChangeBiodata: () -> Unit,
    dataType: String,
    changeDataViewModel: ChangeDataViewModel = koinViewModel()
) {
    var otp = remember { mutableStateListOf<String>("","","","") }
    val changeDataState = changeDataViewModel.changeDataState.collectAsStateWithLifecycle()
    val focusRequesters = List(4) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isDismiss = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }

    if(changeDataState.value.isVerify){
        when(dataType){
            "passData" -> {
                if(changeDataState.value.resetPassTokens != null){
                    navToChangePass(changeDataState.value.resetPassTokens!!)
                }
            }
            "bioData" -> {
                navToChangeBiodata()
            }
        }
    }

    if(changeDataState.value.isLoading){
        popUpLoading(isDismiss)
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
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ){

        Column(
            modifier = Modifier.fillMaxWidth().imePadding()
        ){
            Image(
                painterResource(Res.drawable.otp_icon),"", colorFilter = ColorFilter.tint(Color.White),
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Silahkan Masukan Kode OTP",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1.copy(fontSize = 18.ssp),
            )
            Spacer(
                modifier = Modifier.height(23.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth(),
                text = "Saat ini sedang mengirimkan kode OTP, untuk pemulihan data anda.",
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(
                modifier = Modifier.height(21.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth(),
                text = "Silahkan untuk mengecek email yang masuk diperangkat anda.",
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(
                modifier = Modifier.height(37.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ){
                otp.forEachIndexed { index, value ->
                    OutlinedTextField(
                        modifier = Modifier.focusRequester(focusRequesters[index])
                            .weight(1f)
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == androidx.compose.ui.input.key.Key.Backspace) {
                                    if (otp[index].isEmpty() && index > 0) {
                                        otp[index] = ""
                                        focusRequesters[index - 1].requestFocus()
                                    } else {
                                        otp[index] = ""
                                    }
                                    true
                                } else {
                                    false
                                }
                            },
                        singleLine = true,
                        maxLines = 1,
                        value = value,
                        onValueChange = { otpVl ->
                            if(otpVl.length == 4) {
                                for(i in otp.indices){
                                    otp[i] = if(i < otpVl.length && otpVl[i].isDigit()) otpVl[i].toString() else ""
                                }
                                keyboardController?.hide()
                            } else if(otpVl.length <= 1){
                                otp[index] = otpVl
                                if(otpVl.isNotEmpty()){
                                    if(index < 4 - 1){
                                        focusRequesters[index + 1].requestFocus()
                                    } else {
                                        keyboardController?.hide()
                                    }
                                }
                            } else {
                                if(index < 4 - 1) focusRequesters[index].requestFocus()
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (index == 4 - 1) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (index < 4 - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            },
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        ),
                        textStyle = MaterialTheme.typography.h2.copy(
                            fontSize = 38.sp,
                            textAlign = TextAlign.Center
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = secondaryColor,
                            focusedBorderColor = btnColor,
                            backgroundColor = Color.White,
                            cursorColor = secondaryColor
                        ),
                        shape = RoundedCornerShape(13.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier.height(58.dp)
            )
            Button(
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth().height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    val emptyItems = otp.filter { it.isEmpty() }
                    if(emptyItems.isNotEmpty()){
                        scope.launch {
                            snackBarHostState.showSnackbar("Ada ${emptyItems.size} yang Kosong")
                        }
                    } else {
                        when(dataType){
                            "passData" -> {
                                changeDataViewModel.verifyOtp(otp.joinToString(""),true)
                            }
                            "bioData" -> {
                                changeDataViewModel.verifyOtp(otp.joinToString(""),false)
                            }
                        }

                    }
                },
            ){
                Text(
                    text = "Verifikasi Kode",
                    color = fontColor1,
                    style = MaterialTheme.typography.button
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
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
}