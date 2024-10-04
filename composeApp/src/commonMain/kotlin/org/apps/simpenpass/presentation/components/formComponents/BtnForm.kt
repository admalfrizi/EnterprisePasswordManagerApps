package org.apps.simpenpass.presentation.components.formComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor

@Composable
fun BtnForm(createClick : () -> Unit, cancel: () ->Unit, modifier: Modifier) {
    Box(
        modifier = modifier
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 23.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier.width(153.dp).weight(1f).sizeIn(minHeight = 40.dp),
                onClick = {createClick()},
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(btnColor),
            ){
                Text(
                    text = "Buat Data",
                    color = fontColor1,
                    style = MaterialTheme.typography.button,
                    fontSize = 14.sp
                )
            }
            Spacer(
                modifier = Modifier.width(23.dp)
            )
            Button(
                modifier = Modifier.width(153.dp).sizeIn(minHeight = 40.dp).weight(1f),
                onClick = {
                    cancel()
                },
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
            ){
                Text(
                    text = "Batal",
                    color = fontColor1,
                    style = MaterialTheme.typography.button,
                    fontSize = 14.sp
                )
            }
        }
    }
}