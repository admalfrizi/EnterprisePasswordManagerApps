package org.apps.simpenpass.presentation.components.profileComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.arrow_right_ic

@Composable
fun SettingsListHolder(
    titleSettings: String,
    onClick : () -> Unit = {},
    isSwitch: Boolean = false,
    checked: MutableState<Boolean>? = null,
) {
    Column(modifier = Modifier.fillMaxWidth().clickable {
        onClick()
    }.background(Color.White)) {
        Box {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    titleSettings,
                    style = MaterialTheme.typography.caption,
                    color = secondaryColor
                )
                if(isSwitch == true){
                    Switch(
                        checked = checked?.value == true,
                        onCheckedChange = {
                            checked?.value = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = secondaryColor,
                            uncheckedThumbColor = Color.White
                        )
                    )
                }

                if(isSwitch != true){
                    Image(
                        painter = painterResource(Res.drawable.arrow_right_ic),
                        ""
                    )
                }
            }
        }
        Divider()
    }
}