package org.apps.simpenpass.presentation.ui.group_pass.retrieve_data_pass

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.models.pass_data.DataPass
import org.apps.simpenpass.presentation.components.DialogWarning
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.import_data_pass


@Composable
fun RetrieveDataPass(navController: NavController) {

//    val dataList = listOf(
//        DataPass(1,"Nama Ini", "adam@gmail.com"),
//        DataPass(2,"Ini fewfJuga", "whdkw4t4t@gmail.com"),
//        DataPass(3, "In4t4t4i Juga", "whdkw@gmail.554tcom"),
//        DataPass(4,"Ihthtrhni Juga", "whdkgrgw@gmail.com"),
//        DataPass(5,"Ini fewfJuga", "whdkw4t4t@gmail.com"),
//    )

    val selectedItems = remember {
        mutableStateListOf<DataPass>()
    }

    var isSelectionMode by remember {
        mutableStateOf(false)
    }

    val resetSelectionMode = {
        isSelectionMode = false
        selectedItems.clear()
    }

    var isDialogShow by remember { mutableStateOf(false) }

    LaunchedEffect(
        key1 = isSelectionMode,
        key2 = selectedItems.size
    ){
        if(isSelectionMode && selectedItems.isEmpty()){
            isSelectionMode = false
        }
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelectionMode) Color(0xFF001530) else secondaryColor // Color changes
    )

    if(isDialogShow){
        DialogWarning(
            dialogText = "Data anda akan dipindahkan ke grup, anda yakin ?",
            dialogTitle = "Memindahkan Data",
            onClick = {

            },
            onDismissRequest = {
                isDialogShow = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            TopAppBar(
                backgroundColor = backgroundColor,
                title = {
                    if(isSelectionMode){
                        Text(
                            "${selectedItems.size} Data Dipilih"
                        )
                    } else {
                        Text(
                            "Pilih Data Password"
                        )
                    }

                },
                elevation = 0.dp,
                navigationIcon = {
                    if(isSelectionMode){
                        IconButton(
                            onClick = resetSelectionMode,
                            content = {
                                Image(
                                    Icons.Default.Clear,
                                    "",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        )
                    } else {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            },
                            content = {
                                Image(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    "",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        )
                    }

                },
                actions = {
                    if(isSelectionMode){
                        IconButton(
                            onClick = {
                                isDialogShow = true
                            },
                            content = {
                                Image(
                                    painterResource(Res.drawable.import_data_pass),
                                    "",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        )
                    }
                }
            )
        }
    ){
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ){
//            items(dataList){ item ->
//                DataPassCheckHolder(
//                    dataPass = item,
//                    onLongSelect = {
//
//                    },
//                    isSelected = selectedItems.contains(item),
//                    onSelect = {
//                        if(!isSelectionMode){
//                            isSelectionMode = true
//                        }
//
//                        if (selectedItems.contains(item)) {
//                            selectedItems.remove(item)
//                        } else {
//                            selectedItems.add(item)
//                        }
//                    },
//                    isSelectionMode = isSelectionMode
//                )
//            }
//        }
    }
}

@Composable
fun DataPassCheckHolder(dataPass: DataPass, onLongSelect: () -> Unit, isSelected: Boolean, onSelect: () -> Unit, isSelectionMode: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                if(isSelected) Color(0xFFC0D8F6) else Color.Transparent
            ).clickable {  onSelect() }
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onLongPress = {
//                        onLongSelect()
//                    },
//                    onTap = {
//                        if(isSelectionMode){
//                            onSelect()
//                        }
//                    }
//                )
//            }
    ) {
        Row(modifier = Modifier.padding(vertical = 7.dp, horizontal = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    dataPass.accountName,
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    dataPass.email ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    color = secondaryColor
                )
            }
            Checkbox(
                onCheckedChange = {
                    onSelect()
                },
                checked = isSelected,
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF78A1D7), uncheckedColor = secondaryColor)
            )

        }

    }
}