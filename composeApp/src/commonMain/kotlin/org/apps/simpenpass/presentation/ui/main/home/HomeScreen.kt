package org.apps.simpenpass.presentation.ui.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.apps.simpenpass.presentation.components.homeComponents.HeaderContainer
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_option_ic

@Composable
fun HomeScreen(
    navController: NavHostController,
    sheetState: ModalBottomSheetState,
    homeViewModel: HomeViewModel = koinViewModel()
) {

    val homeState by homeViewModel.homeState.collectAsState()

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
                HeaderContainer(homeState.name,navController)
                Spacer(modifier = Modifier.height(16.dp))

                HomeContentView(navController,sheetState)
                Spacer(
                    modifier = Modifier.height(11.dp)
                )
            }
        }
    )
}

