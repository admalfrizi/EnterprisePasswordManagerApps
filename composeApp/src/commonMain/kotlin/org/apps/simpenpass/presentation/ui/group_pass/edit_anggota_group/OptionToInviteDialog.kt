package org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.apps.simpenpass.presentation.ui.group_pass.MethodSelection
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.edit_ic
import resources.your_data_ic

@Composable
fun OptionToInviteDialog(
    onDismissRequest: () -> Unit,
    navToInviteGroup: () -> Unit,
) {

    val itemsData = listOf(
        MethodSelection(1, Res.drawable.edit_ic, " Kirim Link Melalui Email"),
        MethodSelection(2, Res.drawable.your_data_ic, "Salin Link Invite URL"),
    )
    var selectedOption by remember { mutableStateOf(-1) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Text(
                    "Pilih Opsi untuk Menambahkan Anggota",
                    style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                    textAlign = TextAlign.Start
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(13.dp),
                ) {
                    items(itemsData){ item ->
                        Card(
                            elevation = 0.dp,
                            modifier = Modifier.fillMaxWidth().clickable {
                                selectedOption = item.id
                                if(selectedOption == 1){
                                    navToInviteGroup()
                                }
                            },
                            backgroundColor = Color.Transparent,
                            shape = RoundedCornerShape(9.dp),
                            border = BorderStroke(width = 1.dp, color = Color(0xFF264A97))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(item.icon),
                                    "",
                                    colorFilter = ColorFilter.tint(Color(0xFF264A97))
                                )
                                Spacer(
                                    modifier = Modifier.width(16.dp)
                                )
                                Text(
                                    item.title,
                                    style = MaterialTheme.typography.body1,
                                    color = Color(0xFF264A97)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}