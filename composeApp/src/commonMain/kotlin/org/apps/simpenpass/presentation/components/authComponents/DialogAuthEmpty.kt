package org.apps.simpenpass.presentation.components.authComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.apps.simpenpass.style.primaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.email_empty_ic
import resources.pass_empty_ic

@Composable
fun DialogAuthEmpty(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ){
        Card(
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 17.dp, horizontal = 25.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painterResource(Res.drawable.email_empty_ic),
                        contentDescription = ""
                    )
                    Spacer(
                        modifier = Modifier.width(18.dp)
                    )
                    Divider(
                        thickness = 0.5.dp,
                        modifier = Modifier.width(1.dp).height(53.dp),
                        color = Color(0xFF003376)
                    )
                    Spacer(
                        modifier = Modifier.width(18.dp)
                    )
                    Image(
                        painterResource(Res.drawable.pass_empty_ic),
                        contentDescription = ""
                    )
                }
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Text(
                    "Email dan Password Tidak Boleh Kosong !",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    color = primaryColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}