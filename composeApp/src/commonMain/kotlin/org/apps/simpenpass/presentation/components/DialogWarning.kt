package org.apps.simpenpass.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.primaryColor

@Composable
fun DialogWarning(
    dialogText: String,
    dialogTitle : String,
    onDismissRequest: () -> Unit,
    isCancelBtn: Boolean = true,
    onClick: () -> Unit
) {
    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                dialogTitle,
                style = MaterialTheme.typography.h6,
                color = primaryColor
            )
        },
        text = {
            Text(
                dialogText,
                style = MaterialTheme.typography.subtitle1,
                color = primaryColor
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 24.dp, bottom = 19.dp),
                horizontalArrangement = Arrangement.End
            ){
                if(isCancelBtn){
                    Text(
                        "Tidak",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xFFAF1E20),
                        modifier = Modifier.clickable { onDismissRequest() },
                    )
                    Spacer(
                        modifier = Modifier.width(21.dp)
                    )
                }
                Text(
                    "Ya",
                    modifier = Modifier.clickable { onClick() },
                    style = MaterialTheme.typography.subtitle2,
                    color = primaryColor
                )
            }

        },
        onDismissRequest = onDismissRequest
    )
}