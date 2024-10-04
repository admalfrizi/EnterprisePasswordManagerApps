package org.apps.simpenpass.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.empty_ic
import resources.pass_ic

@Composable
fun EmptyWarning(
    modifier: Modifier = Modifier,
    warnTitle: String,
    warnText: String,
    btnTxt: String? = null,
    isEnableBtn: Boolean = false,
    onSelect: () -> Unit? = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.empty_ic),
            ""
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            warnTitle,
            style = MaterialTheme.typography.button,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            warnText,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.subtitle1,
            color = secondaryColor,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
        if(isEnableBtn){
            Button(
                elevation = ButtonDefaults.elevation(0.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth().height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E78EE)),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    onSelect()
                }
            ) {
                Text(
                    text = btnTxt ?: "",
                    color = fontColor1,
                    style = MaterialTheme.typography.h6
                )
            }
        }

    }
}