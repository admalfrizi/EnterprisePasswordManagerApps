package org.apps.simpenpass.presentation.components.homeComponents

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.fontColor1
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun InfoContainer(
    titleInfo: String,
    vlData: Int,
    bgColor: Color,
    iconInfo : DrawableResource,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.width(132.dp).height(93.dp).clickable {
            onClick()
        },
        backgroundColor = bgColor,
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(11.dp)
        ) {
            Text(
                titleInfo,
                style = MaterialTheme.typography.subtitle2,
                color = fontColor1
            )
            Spacer(
                modifier = Modifier.height(11.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    vlData.toString(),
                    style = MaterialTheme.typography.button
                )
                Image(
                    painter = painterResource(iconInfo),
                    contentDescription = ""
                )
            }
        }
    }
}