package org.apps.simpenpass.presentation.ui.group_pass.pass_data_group_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import org.apps.simpenpass.models.request.VerifySecurityDataGroupRequest
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.groupComponents.GroupLoadingShimmer
import org.apps.simpenpass.presentation.components.rootComponents.DataInfoHolder
import org.apps.simpenpass.presentation.components.rootComponents.PassDataGroupInfoHolder
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.CamelliaCrypto
import org.apps.simpenpass.utils.copyText
import org.apps.simpenpass.utils.getScreenHeight
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.copy_paste
import resources.email_ic
import resources.jenis_data_pass_ic
import resources.pass_ic
import resources.url_link
import resources.user_ic

@OptIn(InternalVoyagerApi::class)
@Composable
fun PassDataDetailsScreen(
    navController: NavController,
    passDataDetailsViewModel: PassDataDetailsViewModel = koinViewModel()
) {
    val passDataDetailsState = passDataDetailsViewModel.passDataDtlState.collectAsState()
    val height = getScreenHeight().value.toInt()
    val isPopUpToDecrypt = remember { mutableStateOf(false) }
    var encKey by remember { mutableStateOf("") }
    var passData by remember { mutableStateOf("") }
    var decData by remember { mutableStateOf("") }

    if(passDataDetailsState.value.passData?.password != null){
        passData = passDataDetailsState.value.passData?.password!!
    }

    if(passDataDetailsState.value.isPassVerify){
        isPopUpToDecrypt.value = false
        passDataDetailsState.value.passData?.isEncrypted = false
        encKey = passDataDetailsState.value.key!!
        decData = CamelliaCrypto().decrypt(passDataDetailsState.value.passData?.password!!,encKey)
        setToast("Data Anda Telah Berhasil Di Dekripsi")
        passDataDetailsState.value.isPassVerify = false
    }

    if(passDataDetailsState.value.key == ""){
        setToast("Data Password anda Tidak Cocok !")
        passDataDetailsState.value.key = null
    }

    if(isPopUpToDecrypt.value){
        VerifyKeyDataToDecrypt(
            onDismissRequest = {
                isPopUpToDecrypt.value = false
            },
            passDataDetailsViewModel
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detail Pass Data Grup",
                        style = MaterialTheme.typography.h6,
                        color = fontColor1
                    )
                },
                backgroundColor = secondaryColor,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            passDataDetailsViewModel.clearState()
                            navController.navigateUp()
                        },
                        content = {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "",
                                modifier = Modifier.padding(8.dp),
                                tint = Color.White
                            )
                        }
                    )
                }
            )
        },
        content = {
            if(passDataDetailsState.value.isLoading == true){
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ){
                    repeat(height / 82){
                        GroupLoadingShimmer()
                        GroupLoadingShimmer()
                        GroupLoadingShimmer()
                    }
                }
            }

            if(passDataDetailsState.value.passData != null && passDataDetailsState.value.isLoading == false) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Jenis telah Disalin")
                            },
                            Res.drawable.jenis_data_pass_ic,
                            passDataDetailsState.value.passData?.jenisData ?: ""
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Username telah Disalin")
                            }, Res.drawable.user_ic, passDataDetailsState.value.passData?.username ?: ""
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Email telah Disalin")
                            }, Res.drawable.email_ic, passDataDetailsState.value.passData?.email ?: ""
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        PassDataGroupInfoHolder(
                            {
                                if(passDataDetailsState.value.passData?.isEncrypted!!){
                                    setToast("Maaf Data Password Masih Terkunci !")
                                } else {
                                    setToast("Data Password telah Disalin")
                                }

                            },
                            Res.drawable.pass_ic,
                            if(decData.isEmpty()) passData else decData,
                            isPopUpToDecrypt,
                            isEncrypted = passDataDetailsState.value.passData?.isEncrypted == true,
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data URL telah Disalin")
                            }, Res.drawable.url_link, passDataDetailsState.value.passData?.url ?: ""
                        )
                        Text(
                            "Deskripsi",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.h6.copy(
                                fontSize = 14.sp,
                                color = secondaryColor
                            )
                        )
                        Spacer(
                            modifier = Modifier.height(11.dp)
                        )
                        Text(
                            passDataDetailsState.value.passData?.desc ?: "Tidak Ada Deskripsi",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontSize = 12.sp,
                                color = secondaryColor
                            )
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        Text(
                            "Tambahan Data",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.h6.copy(
                                fontSize = 14.sp,
                                color = secondaryColor
                            )
                        )
                        Spacer(
                            modifier = Modifier.height(11.dp)
                        )
                    }


                    if(passDataDetailsState.value.passData?.addPassContent?.isEmpty() == true){
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ){
                                EmptyWarning(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    warnTitle = "Data Tambahan Anda Kosong !",
                                    warnText = "Silahkan Tambahkan saat Mengupdate Data",
                                    isEnableBtn = false
                                )
                            }
                        }
                    }

                    if (passDataDetailsState.value.passData?.addPassContent != null) {
                        items(passDataDetailsState.value.passData!!.addPassContent) { item ->
                            Card(
                                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                                backgroundColor = Color(0xFFB7D8F8),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 12.dp
                                    ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            item.nmData,
                                            style = MaterialTheme.typography.body1,
                                            color = secondaryColor
                                        )
                                        Spacer(
                                            modifier = Modifier.height(4.dp)
                                        )
                                        Text(
                                            item.vlData,
                                            style = MaterialTheme.typography.subtitle1,
                                            color = secondaryColor
                                        )
                                    }
                                    IconButton(
                                        content = {
                                            Image(painterResource(Res.drawable.copy_paste), "")
                                        },
                                        onClick = {
                                            copyText(item.vlData)
                                            setToast("Data ${item.nmData} Telah di Salin")
                                        }
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier.height(11.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun VerifyKeyDataToDecrypt(
    onDismissRequest: () -> Unit,
    passDataDetailsViewModel: PassDataDetailsViewModel
) {
    var securityData = remember { mutableStateOf("") }
    var securityValue = remember { mutableStateOf("") }
    var passDataDetailsState = passDataDetailsViewModel.passDataDtlState.collectAsState()

    if(passDataDetailsState.value.dataSecurity == null){
        passDataDetailsViewModel.getSecurityData()
    }

    if(passDataDetailsState.value.dataSecurity?.typeId == 2){
        securityData.value = passDataDetailsState.value.dataSecurity?.securityData!!
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        "Silahkan Masukan Password Anda",
                        style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Icon(
                        Icons.Default.Clear,
                        "",
                        modifier = Modifier.clickable{
                            onDismissRequest()
                        }.clip(CircleShape)
                    )
                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Text(
                    "Data Password anda Telah Terkunci, Silahkan Masukan Kunci untuk Membuka Data Password Anda !",
                    style = MaterialTheme.typography.subtitle1,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                if(passDataDetailsState.value.dataSecurity?.typeId == 2) {
                    Text(
                        passDataDetailsState.value.dataSecurity?.securityData ?: "",
                        style = MaterialTheme.typography.body1,
                        color = secondaryColor
                    )
                }
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                FormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = securityValue.value,
                    labelHints = if(passDataDetailsState.value.dataSecurity?.typeId == 1) "Masukan Password Anda" else "Masukan Jawaban Pertanyaan Di Atas",
                    isPassword = if(passDataDetailsState.value.dataSecurity?.typeId == 1) true else false,
                    leadingIcon = null,
                    onValueChange = {
                        securityValue.value = it
                    }
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        val formVerify = VerifySecurityDataGroupRequest(
                            securityData.value,
                            securityValue.value
                        )

                        passDataDetailsViewModel.verifyPassForDecrypt(formVerify)
                    }
                ) {
                    when(passDataDetailsState.value.isLoading){
                        true -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 3.dp,
                                strokeCap = StrokeCap.Round
                            )
                        }
                        false -> {
                            Text(
                                text = "Verifikasi",
                                color = fontColor1,
                                style = MaterialTheme.typography.button.copy(fontSize = 14.sp)
                            )
                        }

                    }
                }
            }
        }
    }

}