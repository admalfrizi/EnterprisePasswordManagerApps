package org.apps.simpenpass.presentation.ui.group_pass

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import org.apps.simpenpass.presentation.components.groupDtlComponents.OptionAddData
import org.apps.simpenpass.presentation.components.groupDtlComponents.TopBarDtl
import org.apps.simpenpass.presentation.ui.main.group.GroupState
import org.apps.simpenpass.presentation.ui.main.group.GroupViewModel
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.edit_ic
import resources.your_data_ic

@Composable
fun GroupPassDetail(
    navController: NavController,
    groupViewModel: GroupViewModel = koinViewModel(),
    groupId: String
) {
    val tabsName = listOf("Password", "Anggota")
    val groupState by groupViewModel.groupState.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    val itemsData = listOf(
        MethodSelection(1, Res.drawable.edit_ic, " Buat Data Baru"),
        MethodSelection(2, Res.drawable.your_data_ic, "Ambil Dari Data Anda"),
    )

    LaunchedEffect(groupId) {
        groupViewModel.getMemberDataGroup(groupId)
        groupViewModel.getDetailGroup(groupId)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            OptionAddData(scope,sheetState,itemsData,navController)
        },

    ){
        ContentView(navController,tabsName,sheetState,scope,groupState,groupId)
    }
}


@Composable
fun ContentView(
    navController: NavController,
    tabsName: List<String>,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    groupState: GroupState,
    groupId: String,
) {
    var indexTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        backgroundColor = Color(0xFFF1F1F1),
        topBar = {
            TopBarDtl(navController)
        },
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(secondaryColor)
                            .height(43.dp)
                    )
                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 22.dp).fillMaxWidth()
                            .align(
                                Alignment.BottomStart
                            )
                    ) {
                        Box(
                            modifier = Modifier.size(99.dp)
                                .background(color = Color(0xFF78A1D7), shape = CircleShape)
                        ){
                            Text(
                                text = profileNameInitials("fgrf"),
                                style = MaterialTheme.typography.body1,
                                fontSize = 36.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(38.dp))
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                groupState.dtlGroupData?.nm_grup ?: "",
                                style = MaterialTheme.typography.button,
                                color = secondaryColor
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                groupState.dtlGroupData?.desc ?: "",
                                style = MaterialTheme.typography.subtitle1,
                                color = secondaryColor
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Card(
                        modifier = Modifier.width(167.dp).height(93.dp).weight(1f),
                        backgroundColor = Color(0xFF1E559C),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(11.dp).fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Jumlah Data Password",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White
                            )
                            Text(
                                "23",
                                style = MaterialTheme.typography.body2,
                                fontSize = 24.sp
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.width(9.dp)
                    )
                    Card(
                        modifier = Modifier.width(167.dp).height(93.dp).weight(1f),
                        backgroundColor = Color(0xFF1E559C),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(11.dp).fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Jumlah Anggota Grup",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White
                            )
                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )
                            Text(
                                groupState.memberGroupData.size.toString(),
                                style = MaterialTheme.typography.body2,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                TabRow(
                    selectedTabIndex = indexTab,
                    backgroundColor = Color.White,
                    contentColor = secondaryColor,
                ) {
                    tabsName.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    title,
                                    style = MaterialTheme.typography.body1,
                                )
                            },
                            onClick = {
                                indexTab = index
                            },
                            selected = indexTab == index, selectedContentColor = secondaryColor
                        )
                    }
                }
                when (indexTab) {
                    0 -> {
                        PassDataScreen(navController,sheetState,scope)
                    }

                    1 -> {
                        MemberGroupScreen(navController,groupState,groupId)
                    }
                }
            }
        }
    )
}

@Composable
fun ListOptionHolder(icon: DrawableResource, title: String,isSelected : Boolean, onSelected : () -> Unit) {
    Card(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth().clickable {
            onSelected()
        },
        backgroundColor = if(isSelected) Color(0xFF78A1D7) else Color.Transparent,
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFF264A97))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                "",
                colorFilter = ColorFilter.tint(if(isSelected) Color.White else Color(0xFF264A97))
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Text(
                title,
                style = MaterialTheme.typography.body1,
                color = if(isSelected) Color.White else Color(0xFF264A97)
            )
        }
    }
}

data class MethodSelection(
    val id: Int,
    val icon: DrawableResource,
    val title: String
)
