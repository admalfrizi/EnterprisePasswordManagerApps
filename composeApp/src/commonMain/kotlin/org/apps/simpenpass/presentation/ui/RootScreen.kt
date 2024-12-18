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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.theolm.rinku.DeepLink
import org.apps.simpenpass.models.pass_data.DataPass
import org.apps.simpenpass.presentation.components.BottomNavigationBar
import org.apps.simpenpass.presentation.components.rootComponents.RootBottomSheetContent
import org.apps.simpenpass.presentation.components.rootComponents.TopBarNavigationMenu
import org.apps.simpenpass.presentation.ui.main.group.GroupViewModel
import org.apps.simpenpass.presentation.ui.main.group.JoinGroupDialog
import org.apps.simpenpass.presentation.ui.main.home.HomeViewModel
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.RootNavGraph
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.primaryColor
import org.apps.simpenpass.utils.setToast
import org.koin.compose.koinInject

@Composable
fun RootScreen(
    deepLink: MutableState<DeepLink?>,
    bottomEdgeColor: MutableState<Color>,
    navigateToDetailGroupFromPopUp: (String) -> Unit,
    navigateToLogout: () -> Unit,
    navigateToOtpFirst: (String) -> Unit,
    navigateToAddGroup: () -> Unit,
    navigateToGroupDtl: (String) -> Unit,
    navigateToListUserPass : () -> Unit,
    navigateToEditPass: (String) -> Unit,
    navigateToFormPass: () -> Unit,
    groupViewModel: GroupViewModel = koinInject(),
    homeViewModel: HomeViewModel = koinInject()
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
    var isDeleted = remember { mutableStateOf(false) }
    var isPopUp = remember { mutableStateOf(false) }
    val currentScreen = rememberSaveable { mutableStateOf("") }
    var findUrlPath = deepLink.value?.pathSegments?.find { it == "getGroupById" }

    if(currentScreen.value != navController.currentDestination?.route){
        currentScreen.value = navController.currentDestination?.route ?: Screen.Home.route
    }

    bottomEdgeColor.value = primaryColor

    if(sheetState.isVisible){
        bottomEdgeColor.value = Color.White
    }

    if(isJoinDialogPopUp){
        JoinGroupDialog(
            deepLink,
            onDismissRequest = {
                isJoinDialogPopUp = false
                deepLink.value = null
                findUrlPath = ""
            },
            navigateToDetailGroupFromPopUp,
            groupViewModel = groupViewModel
        )
    }

    if(deepLink.value != null && findUrlPath?.isNotEmpty() == true){
        isJoinDialogPopUp = true
    }

    if(homeViewModel.homeState.value.isDeleted){
        isDeleted.value = true
        setToast("Data anda Telah Dihapus")
        homeViewModel.homeState.value.isDeleted = false
    }

    if(!isJoinDialogPopUp){
        groupViewModel.clearState()
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetGesturesEnabled = false,
        sheetContent = {
            checkScreenNav?.let {
                RootBottomSheetContent(
                    checkNavString = it,
                    scope,
                    sheetState,
                    dataDetail,
                    homeViewModel,
                    isPopUp,
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
                currentScreen.value,
                if(!isMainScreen) null else paddingValues,
                sheetState,
                dataDetail,
                isDeleted,
                navigateToFormWithArgs = onClick,
                navigateToLogout,
                navigateToOtpFirst = {
                    navigateToOtpFirst(it)
                },
                navigateToGroupDtl,
                navigateToListUserPass,
                navigateToEditPass = navigateToEditPass,
                navigateToFormPass = navigateToFormPass,
            )
        }
    }
}





