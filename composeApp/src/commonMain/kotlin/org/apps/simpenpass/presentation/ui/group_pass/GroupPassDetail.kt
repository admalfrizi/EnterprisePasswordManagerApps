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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import network.chaintech.sdpcomposemultiplatform.ssp
import org.apps.simpenpass.presentation.components.groupDtlComponents.GroupDtlLoadShimmer
import org.apps.simpenpass.presentation.components.groupDtlComponents.OptionAddData
import org.apps.simpenpass.presentation.components.groupDtlComponents.TopBarDtl
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.Constants
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
    navToBack: () -> Unit,
    navToGroupSettings: (String) -> Unit,
    navToFormGroupPass: (String) -> Unit,
    groupViewModel: GroupDetailsViewModel = koinViewModel(),
    bottomEdgeColor: MutableState<Color>
) {
    val tabsName = listOf("Password", "Anggota")
    val groupState by groupViewModel.groupDtlState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    val itemsData = listOf(
        MethodSelection(1, Res.drawable.edit_ic, " Buat Data Baru"),
        MethodSelection(2, Res.drawable.your_data_ic, "Ambil Dari Data Anda"),
    )
    val snackBarHostState = remember { SnackbarHostState() }

    bottomEdgeColor.value = Color(0xFFF1F1F1)

    if(sheetState.isVisible){
        bottomEdgeColor.value = Color.White
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            if(groupState.groupId != null){
                OptionAddData(
                    scope,
                    sheetState,
                    itemsData,
                    navController,
                    navToFormGroupPass = {
                        navToFormGroupPass(groupState.groupId!!)
                    }
                )
            }
        }
    ){
        ContentView(
            navController,
            navToBack,
            navToGroupSettings = {
                navToGroupSettings(groupState.groupId!!)
            },
            snackBarHostState,
            tabsName,
            sheetState,
            scope,
            groupState,
            groupViewModel
        )
    }
}


@Composable
fun ContentView(
    navController: NavController,
    navToBack: () -> Unit,
    navToGroupSettings: () -> Unit,
    snackbarHostState: SnackbarHostState,
    tabsName: List<String>,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    groupState: GroupDetailsState,
    groupViewModel : GroupDetailsViewModel
) {
    var indexTab by rememberSaveable { mutableStateOf(0) }
    val imagesName = groupState.dtlGroupData?.groupDtl?.img_grup
    var isUserAdmin by remember { mutableStateOf(false) }

    LaunchedEffect(indexTab != 0){
        if(indexTab != 0){
            groupViewModel.getMemberDataGroup(groupState.groupId!!)
        }
    }

    if(groupState.dtlGroupData != null){
        isUserAdmin = groupState.dtlGroupData.isUserAdmin
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        backgroundColor = Color(0xFFF1F1F1),
        topBar = {
            TopBarDtl(
                navToBack,
                navToGroupSettings,
                groupViewModel,
                isUserAdmin
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(secondaryColor)
                            .height(80.dp)
                    ){
                        if(imagesName != null){
                            AsyncImage(
                                model = Constants.IMAGE_URL + imagesName,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .blur(20.dp),
                                contentDescription = "Group Profile Picture",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    if(groupState.isLoading  && groupState.dtlGroupData == null){
                        GroupDtlLoadShimmer()
                    }

                    if(groupState.dtlGroupData != null){
                        Row(
                            modifier = Modifier.padding(start = 16.dp, end= 16.dp, top = 50.dp).fillMaxWidth()
                                .align(
                                    Alignment.BottomStart
                                )
                        ) {
                            Box(
                                modifier = Modifier.size(99.dp)
                                    .background(color = Color(0xFF78A1D7), shape = CircleShape).clip(CircleShape)
                            ){
                                if(imagesName != null){
                                    AsyncImage(
                                        model = Constants.IMAGE_URL + imagesName,
                                        modifier = Modifier.size(99.dp),
                                        contentDescription = "Profile Picture",
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text(
                                        text = profileNameInitials(groupState.dtlGroupData.groupDtl.nm_grup),
                                        style = MaterialTheme.typography.body1,
                                        fontSize = 36.sp,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.width(38.dp))
                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                Spacer(modifier = Modifier.height(28.dp))
                                Text(
                                    groupState.dtlGroupData.groupDtl.nm_grup,
                                    style = MaterialTheme.typography.button,
                                    color = secondaryColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    groupState.dtlGroupData.groupDtl.desc ?: "",
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                            }
                        }
                    }

                }
                Spacer(
                    modifier = Modifier.height(14.dp)
                )
                if(groupState.isLoading  && groupState.dtlGroupData == null){
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).shimmer()) {
                        Box(
                            modifier = Modifier
                                .width(167.dp)
                                .height(93.dp)
                                .weight(1f)
                                .background(Color.LightGray,RoundedCornerShape(10.dp))
                            ,
                        )
                        Spacer(
                            modifier = Modifier.width(9.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(167.dp)
                                .height(93.dp)
                                .weight(1f)
                                .background(Color.LightGray,RoundedCornerShape(10.dp))
                            ,
                        )
                    }
                }
                if(groupState.dtlGroupData != null){
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                        Card(
                            modifier = Modifier.wrapContentHeight().weight(1f),
                            backgroundColor = Color(0xFF1E559C),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(11.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Jumlah Data Password",
                                    style = MaterialTheme.typography.subtitle2.copy(
                                        fontSize = 10.ssp
                                    ),
                                    minLines = 2,
                                    maxLines = 2,
                                    color = Color.White
                                )
                                Text(
                                    groupState.dtlGroupData.totalPassData.toString(),
                                    style = MaterialTheme.typography.body2,
                                    fontSize = 24.sp
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier.width(9.dp)
                        )
                        Card(
                            modifier = Modifier.wrapContentHeight().weight(1f),
                            backgroundColor = Color(0xFF1E559C),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(11.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Jumlah Anggota Grup",
                                    style = MaterialTheme.typography.subtitle2.copy(
                                        fontSize = 10.ssp
                                    ),
                                    minLines = 2,
                                    maxLines = 2,
                                    color = Color.White
                                )
                                Text(
                                    groupState.dtlGroupData.totalMember.toString(),
                                    style = MaterialTheme.typography.body2,
                                    fontSize = 24.sp
                                )
                            }
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
                        PassDataScreen(navController,sheetState,scope,groupState,groupViewModel)
                    }

                    1 -> {
                        MemberGroupScreen(navController,groupState)
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