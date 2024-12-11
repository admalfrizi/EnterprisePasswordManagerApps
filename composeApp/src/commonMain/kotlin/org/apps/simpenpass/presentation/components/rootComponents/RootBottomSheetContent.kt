package org.apps.simpenpass.presentation.components.rootComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.DataPass
import org.apps.simpenpass.presentation.ui.group_pass.ListOptionHolder
import org.apps.simpenpass.presentation.ui.group_pass.MethodSelection
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.setToast
import resources.Res
import resources.delete_pass_data
import resources.edit_anggota_ic
import resources.edit_data_pass
import resources.edit_ic
import resources.email_ic
import resources.join_group_ic
import resources.pass_ic
import resources.url_link
import resources.user_ic

@Composable
fun RootBottomSheetContent(
    checkNavString: String,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    data: MutableState<DataPass?>,
    navigateToToEditForm: MutableState<(DataPass)->Unit>,
    navigateToAddGroup : () -> Unit,
    navigateToJoinGroup : () -> Unit
) {
    if(checkNavString in Screen.Home.route){
        DetailPassData(
            scope,
            sheetState,
            data,
            navigateToToEditForm = navigateToToEditForm,
        )
    } else if(checkNavString in Screen.Group.route){
        AddGroupMethod(
            scope,
            sheetState,
            navigateToAddGroup = navigateToAddGroup,
            navigateToJoinGroup = navigateToJoinGroup
        )
    }
}

@Composable
fun AddGroupMethod(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    navigateToAddGroup: () -> Unit,
    navigateToJoinGroup: () -> Unit
) {
    val itemsData = listOf(
        MethodSelection(1, Res.drawable.edit_ic, " Buat Grup Baru"),
        MethodSelection(2, Res.drawable.join_group_ic, "Gabung dengan Grup Yang Tersedia"),
    )

    var selectedOption by remember { mutableStateOf(-1) }

    if(!sheetState.isVisible){
        selectedOption = -1
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp, bottom = 36.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Silahkan Pilih Cara untuk Bergabung Dengan Grup",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                color = secondaryColor
            )
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                content = {
                    Icon(
                        Icons.Filled.Clear,
                        ""
                    )
                }
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            items(itemsData) { item ->
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ){
                    ListOptionHolder(
                        item.icon,
                        item.title,
                        isSelected = selectedOption == item.id,
                        onSelected =  {
                            selectedOption = item.id
                        }
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.height(31.dp)
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            enabled = selectedOption != -1,
            onClick = {
                if(selectedOption == 1){
                    navigateToAddGroup()
                    scope.launch {
                        sheetState.hide()
                    }
                } else if(selectedOption == 2) {
                    navigateToJoinGroup()
                    scope.launch {
                        sheetState.hide()
                    }
                }
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(btnColor),
            content = {
                Text(
                    "Pilih",
                    style = MaterialTheme.typography.h6,
                    color = fontColor1
                )
            }
        )
    }
}

@Composable
fun DetailPassData(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    data: MutableState<DataPass?>,
    navigateToToEditForm : MutableState<(DataPass)->Unit>
) {

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp, bottom = 36.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                data.value?.accountName ?: "",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                color = secondaryColor
            )
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                content = {
                    Icon(
                        Icons.Filled.Clear,
                        ""
                    )
                }
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        DataInfoHolder(
            {
                setToast("Data Username Telah Disalin")
            },Res.drawable.user_ic,data.value?.username ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            {
                setToast("Data Email Telah Disalin")
            },Res.drawable.email_ic,data.value?.email ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            {
                setToast("Data Password Telah Disalin")
            },
            Res.drawable.pass_ic,
            data.value?.password ?: "",
            isPassData = true
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Divider()
        OptionMenuHolder(
            Res.drawable.url_link,
            "Copy URL"
        )
        OptionMenuHolder(
            Res.drawable.edit_anggota_ic,
            "Pin to Most Used"
        )
        OptionMenuHolder(
            Res.drawable.edit_data_pass,
            "Edit Data Password",
            {
                navigateToToEditForm.value(data.value!!)
                scope.launch {
                    sheetState.hide()
                }
            }
        )
        OptionMenuHolder(
            Res.drawable.delete_pass_data,
            "Hapus Data Password"
        )
    }
}