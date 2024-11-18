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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.presentation.components.homeComponents.HomeLoadingShimmer
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.empty_pass_ic
import resources.menu_ic
import resources.pass_data_ic

@Composable
fun PassDataScreen(
    navController: NavController,
    isShowBottomSheet: ModalBottomSheetState,
    scope: CoroutineScope,
    groupState: GroupDetailsState,
    groupDtlViewModel: GroupDetailsViewModel
) {
    var isAllData = remember { mutableStateOf(true) }
    var passGroupDataId = remember { mutableStateOf("") }
    val isLoading = groupState.listRoleGroup.isNotEmpty() && groupState.groupId != null && groupState.passDataGroup.isEmpty()
    LaunchedEffect(isLoading && isAllData.value) {
        if(isLoading){
            groupDtlViewModel.getAllPassDataGroup(groupState.groupId)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AddPassDataBtnHolder(isShowBottomSheet,scope)
        FilterRow(groupState,groupDtlViewModel,isAllData)
        if(groupState.passDataGroup.isEmpty() && !groupState.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                EmptyDataPassWarning(
                    modifier = Modifier.fillMaxWidth(),)
            }
        }

        if(groupState.isLoading){
            HomeLoadingShimmer()
            HomeLoadingShimmer()
            HomeLoadingShimmer()
        }

        if(groupState.passDataGroup.isNotEmpty() && !groupState.isLoading){
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(groupState.passDataGroup){ data ->
                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
                            navController.navigate(Screen.PassDataGroupDtl.route)
                        }
                    ) {
                        Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(
                                    data?.accountName!!,
                                    style = MaterialTheme.typography.body1,
                                    color = secondaryColor
                                )
                                Spacer(modifier = Modifier.height(7.dp))
                                Text(
                                    data.email ?: "",
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                            }
                            IconButton(
                                onClick = {

                                }
                            ){
                                Image(
                                    painterResource(Res.drawable.menu_ic),""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterRow(
    groupState: GroupDetailsState,
    groupDtlViewModel: GroupDetailsViewModel,
    isAllData: MutableState<Boolean>
) {
    val listRole = groupState.listRoleGroup
    var chipSelected by remember { mutableStateOf(-1) }

    LaunchedEffect(chipSelected != -1 && !isAllData.value){
        if(chipSelected != -1 && !isAllData.value){
            groupDtlViewModel.getPassDataGroupRoleFilter(groupState.groupId!!,chipSelected)
        }
    }

    if(groupState.isLoading && listRole.isEmpty()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp,32.dp)
                    .shimmer()
                    .background(Color.LightGray, shape = RoundedCornerShape(7.dp)),
            )
            Box(
                modifier = Modifier
                    .size(60.dp,32.dp)
                    .shimmer()
                    .background(Color.LightGray, shape = RoundedCornerShape(7.dp)),
            )
            Box(
                modifier = Modifier
                    .size(60.dp,32.dp)
                    .shimmer()
                    .background(Color.LightGray, shape = RoundedCornerShape(7.dp)),
            )
        }
    }

    if(listRole.isNotEmpty()){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listRole){ item ->
                val isSelected = chipSelected == item?.id && isAllData.value == false
                Box(modifier = Modifier.fillMaxWidth()){
                    FilterChip(
                        onClick = {
                            if(isAllData.value == false) {
                                isAllData.value = true
                                groupDtlViewModel.getAllPassDataGroup(groupState.groupId!!)
                            }

                        },
                        selected = isAllData.value == true,
                        colors = ChipDefaults.filterChipColors(backgroundColor = Color.White, selectedBackgroundColor = secondaryColor),
                        shape = RoundedCornerShape(7.dp),
                        border = BorderStroke(color = if(isAllData.value == true) Color.Transparent else Color(0xFF78A1D7), width = 1.dp)
                    ){
                        Text(
                            "Semua",
                            style = MaterialTheme.typography.subtitle2,
                            color = if(isAllData.value == true) Color.White else Color(0xFF78A1D7),
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Box(modifier = Modifier.fillMaxWidth()){
                    FilterChip(
                        onClick = {
                            chipSelected = item?.id!!
                            isAllData.value = false
                        },
                        selected = isSelected,
                        colors = ChipDefaults.filterChipColors(backgroundColor = Color.White, selectedBackgroundColor = secondaryColor),
                        shape = RoundedCornerShape(7.dp),
                        border = BorderStroke(color = if(isSelected) Color.Transparent else Color(0xFF78A1D7), width = 1.dp)
                    ){
                        Text(
                            item?.nmPosisi!!,
                            style = MaterialTheme.typography.subtitle2,
                            color = if(isSelected) Color.White else Color(0xFF78A1D7),
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddPassDataBtnHolder(
    isShowBottomSheet: ModalBottomSheetState,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
            scope.launch {
                isShowBottomSheet.show()
            }
        }
    ){
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(55.dp).background(color = Color(0xFF78A1D7),shape = RoundedCornerShape(7.dp))
            ) {
                Image(
                    painter = painterResource(Res.drawable.pass_data_ic),
                    "",
                    modifier = Modifier.padding(8.dp).size(44.dp)
                )

            }
            Spacer(
                modifier = Modifier.width(28.dp)
            )
            Text(
                "Tambah Data Password",
                style = MaterialTheme.typography.h6,
                fontSize = 13.sp,
                color = secondaryColor
            )
        }
    }
}

@Composable
fun EmptyDataPassWarning(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.empty_pass_ic),
            ""
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            "Data Password anda Kosong",
            style = MaterialTheme.typography.button,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            "Silahkan Buat Data Password yang Baru !",
            style = MaterialTheme.typography.subtitle1,
            color = secondaryColor
        )
    }
}