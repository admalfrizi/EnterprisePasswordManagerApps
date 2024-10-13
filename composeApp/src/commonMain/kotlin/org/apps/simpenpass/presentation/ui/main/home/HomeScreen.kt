package org.apps.simpenpass.presentation.ui.main.home

//import org.apps.simpenpass.presentation.components.homeComponents.MostUsedSection
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.homeComponents.GroupDataSection
import org.apps.simpenpass.presentation.components.homeComponents.HeaderContainer
import org.apps.simpenpass.presentation.components.homeComponents.UserPassDataSection
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.ModalBottomSheetDataValue
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.add_option_ic
import resources.menu_ic

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    sheetState: ModalBottomSheetDataValue<PassResponseData>,
    homeViewModel: HomeViewModel = koinViewModel()
) {

    val homeState by homeViewModel.homeState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeState.isLoading,
        onRefresh = homeViewModel::getData
    )

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
            Box(
                modifier = Modifier.padding(it).pullRefresh(pullRefreshState)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                ) {
                    HeaderContainer(homeState.name,homeState.passDataList.size,navController)
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeContentView(navController,sheetState,homeViewModel)
                    Spacer(
                        modifier = Modifier.height(11.dp)
                    )
                }
                PullRefreshIndicator(
                    refreshing = homeState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = secondaryColor
                )
            }

        }
    )
}

@Composable
fun HomeContentView(
    navController: NavController,
    sheetState: ModalBottomSheetDataValue<PassResponseData>,
    homeViewModel: HomeViewModel
) {
    val homeState by homeViewModel.homeState.collectAsState()

    if(homeState.passDataList.isEmpty() && !homeState.isLoading) {
        EmptyWarning(
            modifier = Modifier.fillMaxSize(),
            warnTitle = "Anda Belum Memiliki Data Password",
            warnText = "Silahkan Tambahkan Data Password Anda melalui Tombol Dibawah",
            btnTxt = "Tambahkan Password",
            isEnableBtn = true,
            onSelect = {
                navController.navigate(Screen.PassData.route)
            }
        )
    }

    if(homeState.passDataList.isNotEmpty()){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
//                MostUsedSection(sheetState)
            GroupDataSection()
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            UserPassDataSection(homeState.passDataList,sheetState,navController)
        }
    }
}

@Composable
fun UserDataPassHolder(dataPass: PassResponseData, sheetState: ModalBottomSheetDataValue<PassResponseData>) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        backgroundColor = Color(0xFFB7D8F8),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    dataPass.accountName,
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Text(
                    dataPass.email ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    color = secondaryColor
                )
            }
            IconButton(
                content = {
                    Image( painterResource(Res.drawable.menu_ic), "")
                },
                onClick = {
                    scope.launch {
                        sheetState.openModal(dataPass)
                    }
                }
            )
        }
    }
    Spacer(
        modifier = Modifier.height(11.dp)
    )
}