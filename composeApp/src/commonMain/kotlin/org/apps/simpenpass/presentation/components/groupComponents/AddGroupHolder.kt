package org.apps.simpenpass.presentation.components.groupComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.add_ic
import resources.group_add_ic

@Composable
fun AddGroupHolder(
    onClick : () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable{
            onClick()
        },
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(58.dp).background(color = Color(0xFF78A1D7),shape = CircleShape)
            ){
                Image(
                    painterResource(Res.drawable.group_add_ic),
                    "",
                    modifier = Modifier.padding(8.dp)
                )
                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .background(color = Color(0xFF195389), shape = CircleShape)
                        .size(20.dp)
                ){
                    Image(
                        painterResource(Res.drawable.add_ic),
                        "",
                        modifier = Modifier.size(8.57.dp).align(Alignment.Center)
                    )
                }
            }
            Spacer(
                modifier = Modifier.width(47.dp)
            )
            Text(
                "Tambah Grup Baru",
                style = MaterialTheme.typography.body1,
                color = secondaryColor
            )
        }
    }
}