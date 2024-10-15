package org.apps.simpenpass.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.presentation.components.BottomNavigationBar
import org.apps.simpenpass.presentation.components.rootComponents.DataInfoHolder
import org.apps.simpenpass.presentation.components.rootComponents.OptionMenuHolder
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.ContentNavGraph
import org.apps.simpenpass.style.secondaryColor
import resources.Res
import resources.delete_pass_data
import resources.edit_anggota_ic
import resources.edit_data_pass
import resources.email_ic
import resources.pass_ic
import resources.url_link
import resources.user_ic

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

    val shouldShowBottomBar = navController.currentBackStackEntryAsState().value?.destination?.route in routeNav.map { it.route }
    val onClick = remember { mutableStateOf<(PassResponseData) -> Unit>({}) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            DetailPassData(scope,sheetState,dataDetail, onClick)
        },
        sheetBackgroundColor = Color.White
    ){
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            },
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            bottomBar = {
                if(shouldShowBottomBar){
                    AnimatedVisibility(visible){
                        BottomNavigationBar(navController, routeNav)
                    }

                }
            }
        ) { paddingValues ->
            ContentNavGraph(navController, if(!shouldShowBottomBar) null else paddingValues,sheetState,dataDetail,snackBarHostState, navigateToFormWithArgs = onClick)
        }
    }
}

@Composable
fun DetailPassData(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    data: MutableState<PassResponseData?>,
    navigateToToEditForm : MutableState<(PassResponseData)->Unit>
) {

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp, bottom = 36.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                data.value?.accountName ?: "",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                color = secondaryColor
            )
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                content = {
                    Icon(
                        Icons.Filled.Clear,
                        ""
                    )
                }
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        DataInfoHolder(
            Res.drawable.user_ic,data.value?.username ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            Res.drawable.email_ic,data.value?.email ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            Res.drawable.pass_ic, data.value?.password ?: "" , isPassData = true
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Divider()
        OptionMenuHolder(
            Res.drawable.url_link,
            "Copy URL"
        )
        OptionMenuHolder(
            Res.drawable.edit_anggota_ic,
            "Pin to Most Used"
        )
        OptionMenuHolder(
            Res.drawable.edit_data_pass,
            "Edit Data Password",
            {
                navigateToToEditForm.value(data.value!!)
                scope.launch {
                    sheetState.hide()
                }
            }
        )
        OptionMenuHolder(
            Res.drawable.delete_pass_data,
            "Hapus Data Password"
        )
    }
}



