package org.apps.simpenpass.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.apps.simpenpass.presentation.components.BottomNavigationBar
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.ContentNavGraph
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.copy_paste
import resources.edit_anggota_ic
import resources.email_ic
import resources.pass_ic
import resources.user_ic
import resources.visibility_ic
import resources.visibility_non_ic

@OptIn(ExperimentalMaterialApi::class)
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

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val shouldShowBottomBar = navController.currentBackStackEntryAsState().value?.destination?.route in routeNav.map { it.route }


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Nama Akun",
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
                    Res.drawable.user_ic,"Username"
                )
                Spacer(
                    modifier = Modifier.height(17.dp)
                )
                DataInfoHolder(
                    Res.drawable.email_ic,"Email"
                )
                Spacer(
                    modifier = Modifier.height(17.dp)
                )
                DataInfoHolder(
                    Res.drawable.pass_ic,"Password", isPassData = true
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Divider()
                OptionMenuHolder(
                    Res.drawable.edit_anggota_ic,
                    "Copy URL"
                )
                OptionMenuHolder(
                    Res.drawable.edit_anggota_ic,
                    "Pin to Most Used"
                )
                OptionMenuHolder(
                    Res.drawable.edit_anggota_ic,
                    "Edit Data Password"
                )
                OptionMenuHolder(
                    Res.drawable.edit_anggota_ic,
                    "Hapus Data Password"
                )
            }
        },
        sheetBackgroundColor = Color.White
    ){
        Scaffold(
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            bottomBar = {
                if(shouldShowBottomBar){
                    AnimatedVisibility(visible){
                        BottomNavigationBar(navController, routeNav)
                    }

                }
            }
        ) { paddingValues ->
            ContentNavGraph(navController, if(!shouldShowBottomBar) null else paddingValues,sheetState)
        }
    }
}

@Composable
fun OptionMenuHolder(icon: DrawableResource,
                     title: String,
) {
    Box(modifier = Modifier.fillMaxWidth().clickable {  },){
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon),"",
                modifier = Modifier.size(44.dp),
                colorFilter = ColorFilter.tint(color = secondaryColor)
            )
            Spacer(
                modifier = Modifier.width(19.dp)
            )
            Text(
                title,
                style = MaterialTheme.typography.subtitle2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )

        }
    }
}

@Composable
fun DataInfoHolder(
    icon: DrawableResource,
    title: String,
    isPassData : Boolean = false
) {
    var showPassword by remember { mutableStateOf(value = false) }

    Box(
        modifier = Modifier.fillMaxWidth().clickable {

        }
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(icon),"",
                modifier = Modifier.size(28.dp)
            )
            Spacer(
                modifier = Modifier.width(19.dp)
            )
            Text(
                title,
                style = MaterialTheme.typography.subtitle2,
                color = secondaryColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            if(isPassData){
                if(showPassword){
                    IconButton(
                        onClick = {
                            showPassword = false
                        }
                    ){
                        Image(
                            painterResource(Res.drawable.visibility_ic),"",
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            showPassword = true
                        }
                    ){
                        Image(
                            painterResource(Res.drawable.visibility_non_ic),"",
                        )
                    }
                }

            }
            IconButton(
                onClick = {

                }
            ){
                Image(
                    painterResource(Res.drawable.copy_paste),"",
                )
            }


        }
    }

}
