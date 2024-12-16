package org.apps.simpenpass.presentation.ui.add_group_security_option

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.apps.simpenpass.models.pass_data.GroupSecurityData
import org.apps.simpenpass.models.request.AddGroupSecurityDataRequest
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.setToast
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddGroupSecurityOption(
    groupId: Int,
    addGroupSecurityViewModel: AddGroupSecurityViewModel = koinInject(),
    securityData: GroupSecurityData?,
    onDismissRequest: () -> Unit
) {
    var addGroupSecurityState = addGroupSecurityViewModel.groupSecurityDataState.collectAsState()
    var expanded = remember { mutableStateOf(false) }
    var toUpdate = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var type = remember { mutableStateOf("") }
    var typeId = remember { mutableStateOf(0) }
    var data = remember { mutableStateOf("") }
    var value = remember { mutableStateOf("") }

    if(addGroupSecurityState.value.listTypeSecurityData.isEmpty()){
        addGroupSecurityViewModel.getTypeSecurityForGroup()
    }

    if(typeId.value == 1){
        data.value = ""
    }

    if(securityData != null){
        val findTypeData = addGroupSecurityState.value.listTypeSecurityData.find { it.id == securityData.typeId }

        toUpdate.value = true
        value.value = securityData.securityValue!!
        typeId.value = securityData.typeId!!
        type.value = findTypeData?.nmOption ?: ""

        if(typeId.value == 2){
            data.value = securityData.securityData
        }
    }
    if(addGroupSecurityState.value.isDeleted){
        onDismissRequest()
        setToast("Data Keamanan Telah Dihapus !")
        addGroupSecurityState.value.isDeleted = false
    }

    if(addGroupSecurityState.value.isUpdated){
        onDismissRequest()
        setToast("Data Keamanan Telah Diubah !")
        addGroupSecurityState.value.isUpdated = false
    }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ){
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        "Masukan Data Keamanan untuk Grup Ini !",
                        style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Spacer(
                        modifier = Modifier.width(40.dp)
                    )
                    Icon(
                        Icons.Default.Clear,
                        "",
                        modifier = Modifier.clickable{
                            if(securityData != null){
                                data.value = ""
                                value.value = ""
                                type.value = ""
                                typeId.value = 0
                                toUpdate.value = false
                            }
                            onDismissRequest()

                        }.clip(CircleShape)
                    )
                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = {
                        expanded.value = it
                    }
                ){
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = type.value,
                        onValueChange = {
                            type.value = it
                        },
                        readOnly = true,
                        placeholder = {
                            Text(
                                text = "Silahkan Pilih Tipe Keamanan" ,
                                color = Color(0xFF9E9E9E),
                                style = MaterialTheme.typography.subtitle1
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = secondaryColor,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Transparent,
                            textColor = Color(0xFF384A92)
                        ),
                        textStyle = MaterialTheme.typography.subtitle1,
                        singleLine = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        )
                    )
                    ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                        if(addGroupSecurityState.value.listTypeSecurityData.isNotEmpty()){
                            addGroupSecurityState.value.listTypeSecurityData.forEach { optionItem ->
                                DropdownMenuItem(
                                    onClick = {
                                        type.value = optionItem.nmOption
                                        typeId.value = optionItem.id

                                        if(securityData != null){
                                            securityData.typeId = optionItem.id
                                        }

                                        expanded.value = false
                                    }
                                ) {
                                    Text(text = optionItem.nmOption)
                                }
                            }
                        }

                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                if(typeId.value == 2){
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = data.value,
                        labelHints =  "Masukan Pertanyaan Keamanan",
                        leadingIcon = null,
                        onValueChange = {

                            if(securityData != null){
                                securityData.securityData = it
                            }

                            data.value = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }
                if(typeId.value == 1 || typeId.value != 0){
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = value.value,
                        labelHints = if(typeId.value == 1) "Masukan Password Anda" else "Masukan Data Jawaban",
                        leadingIcon = null,
                        isPassword = typeId.value == 1,
                        onValueChange = {
                            if(securityData != null){
                                securityData.securityValue = it
                            }

                            value.value = it
                        }
                    )
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        validateInsertData(toUpdate.value,addGroupSecurityViewModel,typeId.value,data.value,value.value,groupId,securityData?.id)
                    },
                    enabled = typeId.value != 0
                ) {
                    when(addGroupSecurityState.value.isLoading){
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
                                text = if(toUpdate.value) "Ubah Data" else "Tambahkan",
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

fun validateInsertData(
    toUpdate : Boolean,
    addGroupSecurityViewModel: AddGroupSecurityViewModel,
    typeId: Int,
    data: String,
    value: String,
    groupId: Int,
    id: Int? = 0
){
    val formData = AddGroupSecurityDataRequest(
        typeId = typeId,
        securityData = data,
        securityValue = value
    )

    when(typeId){
        1 -> {
            if(value.isEmpty()){
                setToast("Data Password Anda Tidak Boleh Kosong")
            } else {
                when(toUpdate){
                    true -> {
                        addGroupSecurityViewModel.updateSecurityDataForGroup(formData,groupId,id!!)
                    }
                    false -> {
                        addGroupSecurityViewModel.addSecurityDataForGroup(formData,groupId)
                    }
                }
            }
        }
        2 -> {
            if(data.isEmpty()){
                setToast("Pertanyaan Tidak Boleh Kosong")
            } else if(value.isEmpty()){
                setToast("Jawaban Tidak Boleh Kosong")
            } else if(data.isEmpty() || value.isEmpty()) {
                setToast("Semua Data Harus Diisi !")
            } else {
                when(toUpdate){
                    true -> {
                        addGroupSecurityViewModel.updateSecurityDataForGroup(formData,groupId,id!!)
                    }
                    false -> {
                        addGroupSecurityViewModel.addSecurityDataForGroup(formData,groupId)
                    }
                }
            }
        }
    }
}