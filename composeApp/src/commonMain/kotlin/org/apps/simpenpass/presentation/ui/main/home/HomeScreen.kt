package org.apps.simpenpass.presentation.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.apps.simpenpass.presentation.components.homeComponents.HeaderContainer
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.add_ic
import resources.add_option_ic
import resources.menu_ic
import resources.option_menu_ic

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
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
                        onClick = { },
                       content = {
                          Icon(
                              Icons.Outlined.Notifications,
                              contentDescription = "",
                              tint = Color.White
                          )
                       }
                    )
                    IconButton(
                        onClick = {navController.navigate(Screen.PassData.route) },
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
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ) {
                HeaderContainer("",navController)
                Spacer(modifier = Modifier.height(16.dp))

                HomeContentView(navController)
                Spacer(
                    modifier = Modifier.height(11.dp)
                )
            }
        }
    )
}

