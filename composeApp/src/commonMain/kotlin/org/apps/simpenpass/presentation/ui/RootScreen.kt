package org.apps.simpenpass.presentation.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import org.apps.simpenpass.models.pass_data.DataPass
import org.apps.simpenpass.presentation.components.BottomNavigationBar
import org.apps.simpenpass.presentation.components.rootComponents.RootBottomSheetContent
import org.apps.simpenpass.presentation.components.rootComponents.TopBarNavigationMenu
import org.apps.simpenpass.presentation.ui.main.group.GroupViewModel
import org.apps.simpenpass.presentation.ui.main.group.JoinGroupDialog
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.RootNavGraph
import org.apps.simpenpass.style.primaryColor
import org.koin.compose.koinInject

@Composable
fun RootScreen(
    bottomEdgeColor: MutableState<Color>,
    navigateToLogout: () -> Unit,
    navigateToAddGroup: () -> Unit,
    navigateToGroupDtl: (String) -> Unit,
    navigateToListUserPass : () -> Unit,
    navigateToEditPass: (String) -> Unit,
    navigateToFormPass: () -> Unit,
    groupViewModel: GroupViewModel = koinInject()
) {
    val navController = rememberNavController()
    val routeNav = listOf(
        BottomNavMenuData.Home,
        BottomNavMenuData.Group,
        BottomNavMenuData.Profile
    )
    val scope = rememberCoroutineScope()
    val dataDetail = remember { mutableStateOf<DataPass?>(null) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val checkScreenNav = navController.currentBackStackEntryAsState().value?.destination?.route
    val isMainScreen = checkScreenNav in routeNav.map { it.route }
    val onClick = remember { mutableStateOf<(DataPass) -> Unit>({}) }
    var isJoinDialogPopUp by remember { mutableStateOf(false) }

    bottomEdgeColor.value = primaryColor

    if(isJoinDialogPopUp){
        JoinGroupDialog(
            onDismissRequest = {
                isJoinDialogPopUp = false
            },
            groupViewModel = groupViewModel
        )
    }

    if(!isJoinDialogPopUp){
        groupViewModel.clearState()
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
                        navigateToAddGroup()
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
            topBar = {
                checkScreenNav?.let {
                    TopBarNavigationMenu(
                        it,
                        navigateToFormPass = navigateToFormPass
                    )
                }
            },
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            bottomBar = {
                BottomNavigationBar(navController, routeNav)
            }
        ) { paddingValues ->
            RootNavGraph(
                navController,
                if(!isMainScreen) null else paddingValues,
                sheetState,
                dataDetail,
                navigateToFormWithArgs = onClick,
                navigateToLogout,
                navigateToGroupDtl,
                navigateToListUserPass,
                navigateToEditPass = navigateToEditPass,
                navigateToFormPass = navigateToFormPass,
            )
        }
    }
}





