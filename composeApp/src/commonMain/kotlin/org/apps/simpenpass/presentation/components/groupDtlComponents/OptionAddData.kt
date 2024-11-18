package org.apps.simpenpass.presentation.components.groupDtlComponents

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.presentation.ui.group_pass.ListOptionHolder
import org.apps.simpenpass.presentation.ui.group_pass.MethodSelection
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor

@Composable
fun OptionAddData(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    itemMenu: List<MethodSelection>,
    navController: NavController,
    navToFormGroupPass: () -> Unit,
) {
    var selectedOption by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier.padding(top = 18.dp, bottom = 36.dp).fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Pilih Metode untuk Menambahkan Data Password Baru", modifier = Modifier.weight(1f).fillMaxWidth(), style = MaterialTheme.typography.h6, color = secondaryColor)
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
            modifier = Modifier.height(13.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            items(itemMenu) { item ->
                ListOptionHolder(
                    icon = item.icon,
                    title = item.title,
                    isSelected = selectedOption == item.id,
                    onSelected = { selectedOption = item.id}
                )
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
                    navToFormGroupPass()
                    scope.launch {
                        sheetState.hide()
                    }
                } else if(selectedOption == 2) {
                    navController.navigate(Screen.RetrieveDataPass.route){
                        restoreState = true
                    }
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
