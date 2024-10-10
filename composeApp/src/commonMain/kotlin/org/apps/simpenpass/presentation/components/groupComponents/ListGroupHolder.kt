package org.apps.simpenpass.presentation.components.groupComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials

@Composable
fun ListGroupHolder(navController: NavController, item: GrupPassData) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth().clickable{
                navController.navigate(Screen.GroupPass.route)
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
                    Text(
                        text = profileNameInitials(item.nm_grup),
                        style = MaterialTheme.typography.body1,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(
                    modifier = Modifier.width(47.dp)
                )
                Column {
                    Text(
                        item.nm_grup,
                        style = MaterialTheme.typography.body1,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )
                    Text(
                        item.desc,
                        style = MaterialTheme.typography.subtitle1,
                        color = secondaryColor
                    )
                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )
                    Text(
                        "Waktu",
                        style = MaterialTheme.typography.subtitle1,
                        color = secondaryColor,
                        fontSize = 10.sp
                    )
                }

            }
        }
        Divider()
    }

}