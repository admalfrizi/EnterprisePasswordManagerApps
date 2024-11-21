package org.apps.simpenpass.presentation.ui.group_pass.pass_data_group_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.groupComponents.GroupLoadingShimmer
import org.apps.simpenpass.presentation.components.rootComponents.DataInfoHolder
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.copyText
import org.apps.simpenpass.utils.getScreenHeight
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.copy_paste
import resources.email_ic
import resources.jenis_data_pass_ic
import resources.pass_ic
import resources.url_link
import resources.user_ic

@OptIn(InternalVoyagerApi::class)
@Composable
fun PassDataDetailsScreen(
    navController: NavController,
    passDataDetailsViewModel: PassDataDetailsViewModel = koinViewModel()
) {
    val passDataDetailsState = passDataDetailsViewModel.passDataDtlState.collectAsState()
    val height = getScreenHeight().value.toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detail Pass Data Grup",
                        style = MaterialTheme.typography.h6,
                        color = fontColor1
                    )
                },
                backgroundColor = secondaryColor,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            passDataDetailsViewModel.clearState()
                            navController.navigateUp()
                        },
                        content = {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "",
                                modifier = Modifier.padding(8.dp),
                                tint = Color.White
                            )
                        }
                    )
                }
            )
        },
        content = {
            if(passDataDetailsState.value.isLoading == true){
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ){
                    repeat(height / 82){
                        GroupLoadingShimmer()
                        GroupLoadingShimmer()
                        GroupLoadingShimmer()
                    }
                }
            }

            if(passDataDetailsState.value.passData != null && passDataDetailsState.value.isLoading == false) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Jenis telah Disalin")
                            },
                            Res.drawable.jenis_data_pass_ic,
                            passDataDetailsState.value.passData?.jenisData ?: ""
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Username telah Disalin")
                            }, Res.drawable.user_ic, passDataDetailsState.value.passData?.username ?: ""
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Email telah Disalin")
                            }, Res.drawable.email_ic, passDataDetailsState.value.passData?.email ?: ""
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data Password telah Disalin")
                            },
                            Res.drawable.pass_ic,
                            passDataDetailsState.value.passData?.password ?: "",
                            isPassData = true
                        )
                        Spacer(
                            modifier = Modifier.height(17.dp)
                        )
                        DataInfoHolder(
                            {
                                setToast("Data URL telah Disalin")
                            }, Res.drawable.url_link, passDataDetailsState.value.passData?.url ?: ""
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
                            passDataDetailsState.value.passData?.desc ?: "Tidak Ada Deskripsi",
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
                    }


                    if(passDataDetailsState.value.passData?.addPassContent?.isEmpty() == true){
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ){
                                EmptyWarning(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    warnTitle = "Data Tambahan Anda Kosong !",
                                    warnText = "Silahkan Tambahkan saat Mengupdate Data",
                                    isEnableBtn = false
                                )
                            }
                        }
                    }

                    if (passDataDetailsState.value.passData?.addPassContent != null) {
                        items(passDataDetailsState.value.passData!!.addPassContent) { item ->
                            Card(
                                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                                backgroundColor = Color(0xFFB7D8F8),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 0.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 12.dp
                                    ),
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
                                            Image(painterResource(Res.drawable.copy_paste), "")
                                        },
                                        onClick = {
                                            copyText(item.vlData)
                                            setToast("Data ${item.nmData} Telah di Salin")
                                        }
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier.height(11.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}