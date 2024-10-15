package org.apps.simpenpass.presentation.components.rootComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun OptionMenuHolder(
    icon: DrawableResource,
    title: String,
    onClick: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxWidth().clickable {
        if (onClick !=null){
            onClick()
        }
    },){
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon),"",
                modifier = Modifier.size(44.dp),
                colorFilter = ColorFilter.tint(color = secondaryColor)
            )
            Spacer(
                modifier = Modifier.width(19.dp)
            )
            Text(
                title,
                style = MaterialTheme.typography.subtitle2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )

        }
    }
}