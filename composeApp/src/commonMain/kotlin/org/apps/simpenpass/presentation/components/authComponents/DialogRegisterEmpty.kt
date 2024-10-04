package org.apps.simpenpass.presentation.components.authComponents

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.apps.simpenpass.style.primaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.empty_warning_ic

@Composable
fun DialogRegisterEmpty(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ){
        Card(
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 17.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(Res.drawable.empty_warning_ic),
                    contentDescription = "",
                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Text(
                    "Tidak boleh ada data yang Kosong !",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    color = primaryColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}