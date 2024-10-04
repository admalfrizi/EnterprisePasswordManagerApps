package org.apps.simpenpass.presentation.ui.list_data_pass_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.models.DataPass
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.copy_paste
import resources.edit_ic
import resources.edit_pass_ic

@Composable
fun DataPassHolder(item : DataPass) {
    Box(modifier = Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(item.accountName,
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
                Text(
                    item.email,
                    style = MaterialTheme.typography.subtitle1,
                    color = secondaryColor
                )
            }
            Row{
                IconButton(
                    onClick = {}
                ){
                    Image(
                        painterResource(Res.drawable.edit_pass_ic),
                        "",
                    )
                }
                IconButton(
                    onClick = {}
                ){
                    Image(
                        painterResource(Res.drawable.copy_paste),
                        "",
                    )
                }
            }

        }
    }
}