package org.apps.simpenpass.presentation.ui.main.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.search_ic

@Composable
fun JoinGroupDialog(onDismissRequest: () -> Unit) {
    var groupName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {onDismissRequest()}
    ){
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Text(
                    "Cari Grup",
                    style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                FormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = groupName,
                    labelHints = "Cari Disini",
                    leadingIcon = {
                        Image(
                            painterResource(Res.drawable.search_ic),
                            ""
                        )
                    },
                    onValueChange = {
                        groupName = it
                    }
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                EmptyWarning(
                    modifier = Modifier.fillMaxWidth(),
                    warnTitle = "Silahkan Cari Nama Grup",
                    warnText = "Info Grup anda Akan Ditampilkan Disini",
                    isEnableBtn = false,
                    onSelect = {}
                )
            }
        }
    }
}