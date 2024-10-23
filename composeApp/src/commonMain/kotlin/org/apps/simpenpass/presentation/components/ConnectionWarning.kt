package org.apps.simpenpass.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.no_internet_ic

@Composable
fun ConnectionWarning(
    modifier: Modifier = Modifier,
    warnTitle: String,
    warnText: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.no_internet_ic),
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            style = MaterialTheme.typography.subtitle1,
            color = secondaryColor,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(21.dp)
        )
    }
}