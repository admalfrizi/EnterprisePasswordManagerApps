package org.apps.simpenpass.presentation.ui.group_pass

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.request.VerifySecurityDataGroupRequest
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.homeComponents.HomeLoadingShimmer
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.CamelliaCrypto
import org.apps.simpenpass.utils.copyText
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.copy_paste
import resources.delete_ic
import resources.edit_pass_ic
import resources.empty_pass_ic
import resources.pass_data_ic

@Composable
fun PassDataScreen(
    navController: NavController,
    isShowBottomSheet: ModalBottomSheetState,
    scope: CoroutineScope,
    groupState: GroupDetailsState,
    groupDtlViewModel: GroupDetailsViewModel
) {
    val isAllData = remember { mutableStateOf(true) }
    var isPopUp by remember { mutableStateOf(false) }
    var passData by remember { mutableStateOf("") }
    var encKey by remember { mutableStateOf("") }
    var decData by remember { mutableStateOf("") }

    if(isPopUp){
        DialogToDecrypt(
            onDismissRequest = {
                isPopUp = false
            },
            groupDtlViewModel
        )
    }

    LaunchedEffect(groupState.passDataGroupId?.toInt() != 0 && groupState.passDataGroupId != null){
        if(groupState.passDataGroupId?.toInt() != 0 && groupState.passDataGroupId != null){
            navController.navigate(Screen.PassDataGroupDtl.passDataGroupId(groupState.passDataGroupId!!,groupState.groupId.toString())){
                popUpTo(Screen.GroupPass.route){
                    inclusive = true
                }
            }
        }
    }

    if(groupState.isPassVerify){
        isPopUp = false
        encKey = groupState.key!!
        decData = CamelliaCrypto().decrypt(passData,encKey)
        copyText(decData)
        setToast("Data password Telah Disalin")
        groupState.isPassVerify = false
    }

    if(groupState.key == ""){
        setToast("Data Password anda Tidak Cocok !")
        groupState.key = null
    }

    if(groupState.isDeleted){
        groupDtlViewModel.getAllPassDataGroup(groupState.groupId!!)
        setToast("Data Password Grup telah dihapus !")
        groupState.isDeleted = false
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AddPassDataBtnHolder(isShowBottomSheet,scope)
        FilterRow(groupState,groupDtlViewModel,isAllData)
        if(groupState.passDataGroup.isEmpty() && !groupState.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                EmptyDataPassWarning(
                    modifier = Modifier.fillMaxWidth(),)
            }
        }

        if(groupState.isLoading){
            HomeLoadingShimmer()
            HomeLoadingShimmer()
            HomeLoadingShimmer()
        }

        if(groupState.passDataGroup.isNotEmpty() && !groupState.isLoading){
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(groupState.passDataGroup){ data ->
                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
                            navController.navigate(Screen.PassDataGroupDtl.passDataGroupId(data?.id.toString(),groupState.groupId.toString()))
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    data?.accountName!!,
                                    style = MaterialTheme.typography.body1,
                                    color = secondaryColor
                                )
                                Spacer(modifier = Modifier.height(7.dp))
                                Text(
                                    data.email ?: "",
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                            }
                            Row {
                                if(groupState.dtlGroupData?.isUserAdmin == true){
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Screen.FormPassGroup.passData(data?.id.toString(),groupState.groupId!!))
                                        }
                                    ){
                                        Image(
                                            painterResource(Res.drawable.edit_pass_ic),""
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        if(data?.isEncrypted!!){
                                            isPopUp = true
                                            passData = data.password!!
                                        } else {
                                            copyText(data.password!!)
                                            setToast("Data password telah disalin")
                                        }
                                    }
                                ){
                                    Image(
                                        painterResource(Res.drawable.copy_paste),""
                                    )
                                }
                                if(groupState.dtlGroupData?.isUserAdmin == true){
                                    IconButton(
                                        onClick = {
                                            groupDtlViewModel.deletePassDataGroup(data?.id!!)
                                        }
                                    ){
                                        Image(
                                            painterResource(Res.drawable.delete_ic),""
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogToDecrypt(
    onDismissRequest: () -> Unit,
    groupDetailsViewModel: GroupDetailsViewModel
) {
    var passDataDetailsState = groupDetailsViewModel.groupDtlState.collectAsState()
    var securityData = remember { mutableStateOf("") }
    var securityValue = remember { mutableStateOf("") }

    if(passDataDetailsState.value.dataSecurity == null){
        groupDetailsViewModel.getSecurityData()
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
                        "Verifikasi Data Keamanan Grup",
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
                    "Data password telah terkunci, silahkan masukan kunci dari data keamanan grup untuk membuka data password",
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
                    isPassword = passDataDetailsState.value.dataSecurity?.typeId == 1,
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

                        groupDetailsViewModel.verifyPassForDecrypt(formVerify)
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

                        else -> {}
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterRow(
    groupState: GroupDetailsState,
    groupDtlViewModel: GroupDetailsViewModel,
    isAllData: MutableState<Boolean>
) {
    val listRole = groupState.listRoleGroup
    var chipSelected by remember { mutableStateOf(-1) }

    if(groupState.isLoading && listRole.isEmpty()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp,32.dp)
                    .shimmer()
                    .background(Color.LightGray, shape = RoundedCornerShape(7.dp)),
            )
            Box(
                modifier = Modifier
                    .size(60.dp,32.dp)
                    .shimmer()
                    .background(Color.LightGray, shape = RoundedCornerShape(7.dp)),
            )
            Box(
                modifier = Modifier
                    .size(60.dp,32.dp)
                    .shimmer()
                    .background(Color.LightGray, shape = RoundedCornerShape(7.dp)),
            )
        }
    }

    if(listRole.isNotEmpty()){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()){
                    FilterChip(
                        onClick = {
                            if(!isAllData.value) {
                                isAllData.value = true
                                groupDtlViewModel.getAllPassDataGroup(groupState.groupId!!)
                            }

                        },
                        selected = isAllData.value,
                        colors = ChipDefaults.filterChipColors(backgroundColor = Color.White, selectedBackgroundColor = secondaryColor),
                        shape = RoundedCornerShape(7.dp),
                        border = BorderStroke(color = if(isAllData.value) Color.Transparent else Color(0xFF78A1D7), width = 1.dp)
                    ){
                        Text(
                            "Semua",
                            style = MaterialTheme.typography.subtitle2,
                            color = if(isAllData.value) Color.White else Color(0xFF78A1D7),
                        )
                    }
                }
            }

            items(listRole){ item ->
                val isSelected = chipSelected == item?.id && !isAllData.value
                Box(modifier = Modifier.fillMaxWidth()){
                    FilterChip(
                        onClick = {
                            chipSelected = item?.id!!
                            isAllData.value = false
                            groupDtlViewModel.getPassDataGroupRoleFilter(groupState.groupId!!,chipSelected)
                        },
                        selected = isSelected,
                        colors = ChipDefaults.filterChipColors(backgroundColor = Color.White, selectedBackgroundColor = secondaryColor),
                        shape = RoundedCornerShape(7.dp),
                        border = BorderStroke(color = if(isSelected) Color.Transparent else Color(0xFF78A1D7), width = 1.dp)
                    ){
                        Text(
                            item?.nmPosisi!!,
                            style = MaterialTheme.typography.subtitle2,
                            color = if(isSelected) Color.White else Color(0xFF78A1D7),
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddPassDataBtnHolder(
    isShowBottomSheet: ModalBottomSheetState,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
            scope.launch {
                isShowBottomSheet.show()
            }
        }
    ){
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(55.dp).background(color = Color(0xFF78A1D7),shape = RoundedCornerShape(7.dp))
            ) {
                Image(
                    painter = painterResource(Res.drawable.pass_data_ic),
                    "",
                    modifier = Modifier.padding(8.dp).size(44.dp)
                )

            }
            Spacer(
                modifier = Modifier.width(28.dp)
            )
            Text(
                "Tambah Data Password",
                style = MaterialTheme.typography.h6,
                fontSize = 13.sp,
                color = secondaryColor
            )
        }
    }
}

@Composable
fun EmptyDataPassWarning(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.empty_pass_ic),
            ""
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            "Data Password anda Kosong",
            style = MaterialTheme.typography.button,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            "Silahkan Buat Data Password yang Baru !",
            style = MaterialTheme.typography.subtitle1,
            color = secondaryColor
        )
    }
}