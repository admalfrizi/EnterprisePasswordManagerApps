package org.apps.simpenpass.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun DialogLoading(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest }
    ){
        Card(
            shape = RoundedCornerShape(20.dp)
        ){
            Row(
                modifier = Modifier.padding(24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = secondaryColor
                )
                Spacer(modifier = Modifier.width(28.dp))
                Text(
                    "Sedang Memproses...",
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
            }
        }

    }
}