package org.apps.simpenpass.presentation.components.profileComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.add_ic
import resources.group_add_ic

@Composable
fun HeaderContainer(nameUser: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(color = secondaryColor)
    ){
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(
                modifier = Modifier.height(18.dp)
            )
            Row {
                Box(
                    modifier = Modifier.size(76.dp).background(color = Color(0xFF78A1D7),shape = CircleShape)
                ){
                    Text(
                        text = profileNameInitials("Armando Verrera"),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(
                    modifier = Modifier.width(24.dp)
                )
                Column(
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Text(
                        "$nameUser",
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    Text(
                        "Email",
                        style = MaterialTheme.typography.subtitle1,
                        color = fontColor1,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                    Spacer(
                        modifier = Modifier.height(23.dp)
                    )
                }
            }

        }

    }
}

