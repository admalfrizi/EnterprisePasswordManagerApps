package org.apps.simpenpass.presentation.ui.main.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import dev.theolm.rinku.DeepLink
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.formComponents.FormTextField
import org.apps.simpenpass.presentation.components.groupComponents.SearchLoadingGroup
import org.apps.simpenpass.style.btnColor
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.profileNameInitials
import org.apps.simpenpass.utils.setToast
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.search_ic

@Composable
fun JoinGroupDialog(
    deepLink: MutableState<DeepLink?>,
    onDismissRequest: () -> Unit,
    navigateToDetailGroup: (String) -> Unit,
    groupViewModel: GroupViewModel
) {
    var groupName by remember { mutableStateOf("") }
    val groupState by groupViewModel.groupState.collectAsState()
    var urlCheck = deepLink.value?.pathSegments?.find { it == "getGroupById" }

    LaunchedEffect(deepLink.value?.data != null && urlCheck?.isNotEmpty()!!){
        if(deepLink.value?.data != null && urlCheck?.isNotEmpty()!!){
            groupViewModel.getGroupById(deepLink.value?.pathSegments[2]?.toInt()!!)
        }
    }

    if(groupState.isJoined){
        groupViewModel.getGroupById(deepLink.value?.pathSegments[2]?.toInt()!!)
        setToast("Anda Telah Bergabung Ke Group")
        groupState.isJoined = false
    }

    Dialog(
        onDismissRequest = {onDismissRequest()},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Text(
                    "Cari Grup",
                    style = MaterialTheme.typography.h6.copy(color = secondaryColor),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                if(urlCheck == null){
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = groupName,
                        labelHints = "Cari Disini",
                        leadingIcon = {
                            Image(
                                painterResource(Res.drawable.search_ic),
                                ""
                            )
                        },
                        onValueChange = {
                            groupName = it
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )
                }

                if(groupState.searchGroupResult == null && !groupState.isError && !groupState.isLoading){
                    EmptyWarning(
                        modifier = Modifier.fillMaxWidth(),
                        warnTitle = "Silahkan Cari Nama Grup",
                        warnText = "Info Grup akan Ditampilkan Disini",
                        isEnableBtn = false,
                        onSelect = {}
                    )
                }

                if(groupState.isLoading){
                    SearchLoadingGroup(
                        modifier = Modifier.fillMaxWidth().shimmer().padding(horizontal = 16.dp),
                    )
                }

                if(groupState.searchGroupResult != null && !groupState.isLoading){
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(68.dp)
                                .background(color = Color(0xFF78A1D7),shape = CircleShape).clip(CircleShape)
                        ){
                            if(groupState.searchGroupResult?.img_grup != null){
                                AsyncImage(
                                    model = groupState.searchGroupResult?.img_grup,
                                    modifier = Modifier.size(99.dp),
                                    contentDescription = "Profile Picture",
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(
                                    text = profileNameInitials(groupState.searchGroupResult!!.nm_grup),
                                    style = MaterialTheme.typography.body1,
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                        }
                        Text(
                            groupState.searchGroupResult!!.nm_grup,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.h6,
                            color = secondaryColor,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Total ${groupState.searchGroupResult?.totalAnggotaGroup} Anggota",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.subtitle1,
                            color = secondaryColor,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        onClick = {
                            if(groupState.searchGroupResult?.isMemberJoined!!){
                                navigateToDetailGroup(groupState.searchGroupResult?.id.toString())
                                deepLink.value = null
                            } else {
                                groupViewModel.userJoinToGroup(groupState.searchGroupResult?.id!!)
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.elevation(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF1E78EE)),
                        content = {
                            Text(
                                if(groupState.searchGroupResult?.isMemberJoined!!) "Detail Grup" else "Gabung Ke Grup",
                                style = MaterialTheme.typography.h6,
                                color = fontColor1
                            )
                        }
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClick = {
                        if(groupName.isNotEmpty()){
                            groupViewModel.searchGroup(groupName)
                        } else if(groupName.isEmpty()) {
                            setToast("Isi Nama Grup yang Ingin Dicari")
                        } else if(groupState.searchGroupResult == null){
                            setToast("Hasil Pencarian Tidak Ditemukan")
                        }

                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(btnColor),
                    content = {
                        Text(
                            "Cari",
                            style = MaterialTheme.typography.h6,
                            color = fontColor1
                        )
                    }
                )

            }
        }
    }
}