package org.apps.simpenpass.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.presentation.components.BottomNavigationBar
import org.apps.simpenpass.presentation.components.rootComponents.RootBottomSheetContent
import org.apps.simpenpass.presentation.components.rootComponents.TopBarNavigationMenu
import org.apps.simpenpass.presentation.ui.main.group.JoinGroupDialog
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.ContentNavGraph
import org.apps.simpenpass.screen.Screen

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val routeNav = listOf(
        BottomNavMenuData.Home,
        BottomNavMenuData.Group,
        BottomNavMenuData.Profile
    )
    val visible by remember {
        mutableStateOf(true)
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val dataDetail = remember { mutableStateOf<PassResponseData?>(null) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

    val checkScreenNav = navController.currentBackStackEntryAsState().value?.destination?.route
    val isMainScreen = checkScreenNav in routeNav.map { it.route }
    val onClick = remember { mutableStateOf<(PassResponseData) -> Unit>({}) }
    var isJoinDialogPopUp by remember { mutableStateOf(false) }

    if(isJoinDialogPopUp){
        JoinGroupDialog {
            isJoinDialogPopUp = false
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            checkScreenNav?.let {
                RootBottomSheetContent(
                    checkNavString = it,
                    scope,
                    sheetState,
                    dataDetail,
                    onClick,
                    navigateToAddGroup = {
                        navController.navigate(Screen.AddGroupPass.route)
                    },
                    navigateToJoinGroup = {
                        isJoinDialogPopUp = true
                    }
                )
            }
        },
        sheetBackgroundColor = Color.White
    ){
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            },
            topBar = {
                checkScreenNav?.let {
                    TopBarNavigationMenu(
                        it,
                        navigateToFormPass = {
                            navController.navigate(Screen.FormPassData.route)
                        }
                    )
                }
            },
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            bottomBar = {
                if(isMainScreen){
                    AnimatedVisibility(visible){
                        BottomNavigationBar(navController, routeNav)
                    }

                }
            }
        ) { paddingValues ->
            ContentNavGraph(navController, if(!isMainScreen) null else paddingValues,sheetState,dataDetail,snackBarHostState, navigateToFormWithArgs = onClick)
        }
    }
}





