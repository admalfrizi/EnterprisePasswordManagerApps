package org.apps.simpenpass.presentation.components.rootComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.add_option_ic

@Composable
fun TopBarNavigationMenu(
    checkNavString: String,
    navigateToFormPass : () -> Unit
) {
    if(checkNavString in Screen.Home.route){
        TopBarHome(
            navigateToFormPass
        )
    } else if(checkNavString in Screen.Group.route){
        TopBarGroup()
    } else if(checkNavString in Screen.Profile.route){
        TopBarProfile()
    }
}

@Composable
fun TopBarHome(
    navigateToFormPass: () -> Unit
) {
    TopAppBar(
        backgroundColor = secondaryColor,
        title = {
            Text(
                BottomNavMenuData.Home.title,
                style = MaterialTheme.typography.h6,
                color = fontColor1
            )
        },
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick = { navigateToFormPass()
                          },
                content = {
                    Icon(
                        painterResource(Res.drawable.add_option_ic),
                        contentDescription = "",
                        modifier = Modifier.padding(8.dp),
                        tint = Color.White
                    )
                }
            )
        }
    )
}

@Composable
fun TopBarGroup(){
    TopAppBar(
        backgroundColor = secondaryColor,
        elevation = 0.dp,
        title = {
            Text(
                "Grup Data Password",
                style = MaterialTheme.typography.h6,
                color = fontColor1
            )
        }
    )
}

@Composable
fun TopBarProfile() {
    TopAppBar(
        backgroundColor = secondaryColor,
        elevation = 0.dp,
        title = {
            Text(
                "Data Profil",
                style = MaterialTheme.typography.h6,
                color = fontColor1
            )
        }
    )
}