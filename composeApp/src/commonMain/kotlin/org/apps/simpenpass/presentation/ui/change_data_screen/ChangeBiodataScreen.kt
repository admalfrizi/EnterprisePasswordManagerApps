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
import androidx.compose.runtime.LaunchedEffect
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
import io.github.aakira.napier.Napier
import org.apps.simpenpass.models.request.UpdateUserDataRequest
import org.apps.simpenpass.presentation.components.CustomTextField
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.primaryColor
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.email_ic
import resources.user_ic

@Composable
fun ChangeBiodataScreen(
    navToBack: () -> Unit,
    navToHome : () -> Unit,
    changeDataViewModel: ChangeDataViewModel = koinViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    var emailData by remember { mutableStateOf("") }
    var nmData by remember { mutableStateOf("") }
    val changeDataState by changeDataViewModel.changeDataState.collectAsState()
    val isDismiss = remember { mutableStateOf(true) }
    var isPopUp by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        changeDataViewModel.getUserData()
    }

    if(changeDataState.updateData != null){
        nmData = changeDataState.updateData?.name ?: ""
        emailData = changeDataState.updateData?.email ?: ""
    }

    if(changeDataState.isLoading){
        popUpLoading(isDismiss)
    }

    if(changeDataState.isSuccess){
        isPopUp = true
        changeDataViewModel.saveUserData(changeDataState.userData!!)
    }

    if(isPopUp){
        SuccessUpdateUser(
            {
                isPopUp = false
            },
            {
                navToHome()
                isPopUp = false
            }
        )
    }

    Napier.v("response : ${changeDataState.userData}")
    Napier.v("updateData : ${UpdateUserDataRequest(nmData,emailData)}")
//    var updateData = UpdateUserDataRequest(nmData,emailData)
//

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
                text = "Silahkan Ubah Data Anda",
                style = MaterialTheme.typography.h1,
                fontSize = 32.sp
            )
            Spacer(
                modifier = Modifier.height(35.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Masukan data anda untuk melakukan pembaharuan data pada akun anda",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Spacer(
                modifier = Modifier.height(57.dp)
            )
            CustomTextField(
                interactionSource = interactionSource,
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                labelHints = "Masukan Nama Baru",
                value = nmData,
                leadingIcon =  {
                    Icon(
                        painterResource(Res.drawable.user_ic),
                        tint = Color(0xFF384A92),
                        contentDescription = ""
                    )
                },
                isFocus = false,
                onValueChange = {
                    if(changeDataState.updateData != null){
                        changeDataState.updateData?.name = it
                    }

                    nmData = it
                },
                focusColor = Color(0xFF4433DB)
            )
            Spacer(
                modifier = Modifier.height(37.dp)
            )
            CustomTextField(
                interactionSource = interactionSource,
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                labelHints = "Masukan Email Baru",
                value = emailData,
                leadingIcon =  {
                    Icon(
                        painterResource(Res.drawable.email_ic),
                        tint = Color(0xFF384A92),
                        contentDescription = ""
                    )
                },
                isFocus = false,
                onValueChange = {
                    if(changeDataState.updateData != null){
                        changeDataState.updateData?.email = it
                    }

                    emailData = it
                },
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
                    changeDataViewModel.updateDataUser(changeDataState.userId!!,UpdateUserDataRequest(nmData,emailData))
                }
            ){
                Text(
                    text = "Reset Data",
                    color = fontColor1,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun SuccessUpdateUser(
    onDismiss : () -> Unit,
    onClick : () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                "Data Anda Telah Sukses di Perbaharui",
                style = MaterialTheme.typography.h6,
                color = primaryColor
            )
        },
        text = {
            Text(
                "Silahkan Kembali Ke Halaman Utama",
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