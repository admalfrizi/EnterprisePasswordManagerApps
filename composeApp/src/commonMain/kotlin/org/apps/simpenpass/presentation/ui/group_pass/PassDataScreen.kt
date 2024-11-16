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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.presentation.ui.main.group.GroupState
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.empty_pass_ic
import resources.pass_data_ic

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PassDataScreen(
    navController: NavController,
    isShowBottomSheet: ModalBottomSheetState,
    scope: CoroutineScope,
    groupState: GroupState
) {
//    val dataList = listOf(
//        DataPass(1,"Nama Ini", "adam@gmail.com"),
//        DataPass(2,"Ini fewfJuga", "whdkw4t4t@gmail.com"),
//        DataPass(3, "In4t4t4i Juga", "whdkw@gmail.554tcom"),
//        DataPass(4,"Ihthtrhni Juga", "whdkgrgw@gmail.com"),
//        DataPass(5,"Ini fewfJuga", "whdkw4t4t@gmail.com"),
//    )

//    val dataList by remember { mutableStateOf(emptyList<DataPass>()) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AddPassDataBtnHolder(isShowBottomSheet,scope)
        FilterRow(groupState)
//        if(dataList.isEmpty()){
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center,
//            ) {
//                EmptyDataPassWarning(
//                    modifier = Modifier.fillMaxWidth(),)
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                items(dataList){ data ->
//                    Box(
//                        modifier = Modifier.fillMaxWidth().background(Color.White)
//                    ) {
//                        Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                            Column {
//                                Text(
//                                    data.accountName,
//                                    style = MaterialTheme.typography.body1,
//                                    color = secondaryColor
//                                )
//                                Spacer(modifier = Modifier.height(7.dp))
//                                Text(
//                                    data.email,
//                                    style = MaterialTheme.typography.subtitle1,
//                                    color = secondaryColor
//                                )
//                            }
//                            IconButton(
//                                onClick = {}
//                            ){
//                                Image(
//                                    painterResource(Res.drawable.menu_ic),""
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }


    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterRow(
    groupState: GroupState,
) {
    val listRole = groupState.listRoleGroup
    var chipSelected by remember { mutableStateOf(-1) }
    var isAllData by remember { mutableStateOf(true) }

    if(listRole.isNotEmpty()){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listRole){ item ->
                val isSelected = chipSelected == item?.id && isAllData == false
                Box(modifier = Modifier.fillMaxWidth()){
                    FilterChip(
                        onClick = {
                            if(isAllData == true){
                                isAllData = false
                            } else {
                                isAllData = true
                            }

                        },
                        selected = isAllData == true,
                        colors = ChipDefaults.filterChipColors(backgroundColor = Color.White, selectedBackgroundColor = secondaryColor),
                        shape = RoundedCornerShape(7.dp),
                        border = BorderStroke(color = if(isAllData == true) Color.Transparent else Color(0xFF78A1D7), width = 1.dp)
                    ){
                        Text(
                            "Semua",
                            style = MaterialTheme.typography.subtitle2,
                            color = if(isAllData == true) Color.White else Color(0xFF78A1D7),
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
                            isAllData = false
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
fun AddPassDataBtnHolder(isShowBottomSheet: ModalBottomSheetState, scope: CoroutineScope) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
//            navController.navigate(Screen.EditAnggota.route)
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