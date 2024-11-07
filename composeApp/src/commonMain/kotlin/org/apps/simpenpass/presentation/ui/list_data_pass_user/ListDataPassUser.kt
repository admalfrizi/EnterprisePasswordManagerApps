package org.apps.simpenpass.presentation.ui.list_data_pass_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.response.DataPassWithAddContent
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.rootComponents.DataInfoHolder
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.copy_paste
import resources.email_ic
import resources.jenis_data_pass_ic
import resources.menu_ic
import resources.pass_ic
import resources.url_link
import resources.user_ic

@Composable
fun ListDataPassUser(
    bottomEdgeColor: MutableState<Color>,
    navigateToFormEdit: (String) -> Unit,
    navigateBack: () -> Unit,
    listDataViewModel: ListDataViewModel = koinViewModel()
) {
    val state by listDataViewModel.listDataState.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    var isDropdownShow by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val dataDetail = remember { mutableStateOf<DataPassWithAddContent?>(null) }

    bottomEdgeColor.value = Color.White

    ModalBottomSheetLayout(
        sheetContent = {
            PassDataInfo(scope,sheetState,dataDetail)
        },
        sheetElevation = 0.dp,
        sheetGesturesEnabled = false,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = sheetState,
    ){
        Scaffold(
            modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.safeDrawing),
            topBar = {
                TopAppBar(
                    backgroundColor = secondaryColor,
                    title = {
                        Text(
                            "Data Password Anda"
                        )
                    },
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigateBack()
                            },
                            content = {
                                Image(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    "",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                isDropdownShow = true
                            },
                            content = {
                                Image(
                                    painterResource(Res.drawable.menu_ic),
                                    "",
                                    colorFilter = ColorFilter.tint(Color.White))}
                        )

                        DropdownMenu(
                            expanded = isDropdownShow,
                            onDismissRequest = { isDropdownShow = false }
                        ) {
                            DropdownMenuItem(
                                content = {
                                    Text(text = "Hapus Data Password")
                                },
                                onClick = {
                                }
                            )
                            DropdownMenuItem(
                                content = {
                                    Text(text = "Tambah Data Baru")
                                },
                                onClick = {
                                }
                            )
                        }
                    }
                )
            },
            content = {
                if(state.data.isEmpty() && !state.isLoading){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        EmptyWarning(
                            modifier = Modifier.fillMaxWidth(),
                            warnTitle = "Data Password anda Kosong",
                            warnText = "Silahkan membuat Data Password yang baru !",
                            isEnableBtn = true,
                            btnTxt = "Buat Data Password Baru",
                            onSelect = {

                            }
                        )
                    }
                }

                if(state.data.isNotEmpty()){
                    LazyColumn {
                        items(state.data){ item ->
                            DataPassHolder(item, dataDetail, sheetState, scope, navigateToFormEdit)
                        }
                    }
                }

                if(state.isLoading){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        )
    }

}

@Composable
fun PassDataInfo(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    data: MutableState<DataPassWithAddContent?>
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp, bottom = 36.dp),
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
            {
                setToast("Data Jenis telah Disalin")
            },Res.drawable.jenis_data_pass_ic,data.value?.jenisData ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            {
                setToast("Data Username telah Disalin")
            },Res.drawable.user_ic,data.value?.username ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            {
                setToast("Data Email telah Disalin")
            }, Res.drawable.email_ic,data.value?.email ?: ""
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            {
                setToast("Data Password telah Disalin")
            },Res.drawable.pass_ic, data.value?.password ?: "" , isPassData = true
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        DataInfoHolder(
            {
                setToast("Data URL telah Disalin")
            },Res.drawable.url_link, data.value?.url ?: ""
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            "Deskripsi",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.h6.copy(
                fontSize = 14.sp,
                color = secondaryColor
            )
        )
        Spacer(
            modifier = Modifier.height(11.dp)
        )
        Text(
            data.value?.desc ?: "Tidak Ada Deskripsi",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 12.sp,
                color = secondaryColor
            )
        )
        Spacer(
            modifier = Modifier.height(17.dp)
        )
        Text(
            "Tambahan Data",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.h6.copy(
                fontSize = 14.sp,
                color = secondaryColor
            )
        )
        Spacer(
            modifier = Modifier.height(11.dp)
        )
        if(data.value?.addContentPass != null){
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().heightIn(
                    max = (3 * 86).dp
                ),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ){
                items(data.value?.addContentPass!!){ item ->
                    Card(
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        backgroundColor = Color(0xFFB7D8F8),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    item.nmData,
                                    style = MaterialTheme.typography.body1,
                                    color = secondaryColor
                                )
                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )
                                Text(
                                    item.vlData,
                                    style = MaterialTheme.typography.subtitle1,
                                    color = secondaryColor
                                )
                            }
                            IconButton(
                                content = {
                                    Image( painterResource(Res.drawable.copy_paste), "")
                                },
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}