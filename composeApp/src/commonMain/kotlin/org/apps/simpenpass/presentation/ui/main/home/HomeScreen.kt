package org.apps.simpenpass.presentation.ui.main.home

//import org.apps.simpenpass.presentation.components.homeComponents.MostUsedSection
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.apps.simpenpass.models.pass_data.DataPass
import org.apps.simpenpass.presentation.components.ConnectionWarning
import org.apps.simpenpass.presentation.components.EmptyWarning
import org.apps.simpenpass.presentation.components.homeComponents.GroupDataSection
import org.apps.simpenpass.presentation.components.homeComponents.HeaderContainer
import org.apps.simpenpass.presentation.components.homeComponents.HomeLoadingShimmer
import org.apps.simpenpass.presentation.components.homeComponents.UserPassDataSection
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import resources.Res
import resources.menu_ic

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    sheetState: ModalBottomSheetState,
    dataPass: MutableState<DataPass?>,
    isDeleted: MutableState<Boolean>,
    homeViewModel: HomeViewModel = koinViewModel(),
    passDataId: MutableState<(DataPass) -> Unit>,
    navigateToGrupDtl: (String, String) -> Unit,
    navigateToFormEdit: (String) -> Unit,
    navigateToForm: () -> Unit,
    navigateToListUserPass : () -> Unit
) {
    val homeState by homeViewModel.homeState.collectAsStateWithLifecycle()
    val isConnected by homeViewModel.isConnected.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeState.isLoading,
        onRefresh = {
            homeViewModel.getData()
            homeViewModel.getRecommendationPassDataGroup()
            homeViewModel.getUserDataStats(homeState.id!!)
        }
    )

    LaunchedEffect(isConnected) {
        if(isConnected){
            homeViewModel.getData()
            homeViewModel.getRecommendationPassDataGroup()
        }
    }

    LaunchedEffect(homeState.id != null){
        if(homeState.id != null){
            homeViewModel.getUserDataStats(homeState.id!!)
        }
    }

    passDataId.value = { passData ->
        navigateToFormEdit(passData.id.toString())
    }

    if(isDeleted.value){
        homeViewModel.getData()
        homeViewModel.getUserDataStats(homeState.id!!)
        homeViewModel.getRecommendationPassDataGroup()
        isDeleted.value = false
    }

    Scaffold(
        content = {
            Box(
                modifier = Modifier.padding(it).pullRefresh(pullRefreshState)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                ) {
                    HeaderContainer(
                        homeState,
                        navigateToListUserPass
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeContentView(
                        isConnected,
                        sheetState,
                        dataPass,
                        homeViewModel,
                        navigateToListUserPass,
                        navigateToForm,
                        navigateToGrupDtl
                    )
                    Spacer(
                        modifier = Modifier.height(11.dp)
                    )
                }
                PullRefreshIndicator(
                    refreshing = homeState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = secondaryColor
                )
            }

        }
    )
}

@Composable
fun HomeContentView(
    isConnected: Boolean,
    sheetState: ModalBottomSheetState,
    dataPass: MutableState<DataPass?>,
    homeViewModel: HomeViewModel,
    navigateToListUserPass: () -> Unit,
    navigateToFormPass: () -> Unit,
    navigateToGrupDtl: (String, String) -> Unit
) {
    val homeState by homeViewModel.homeState.collectAsState()

    when(homeState.isLoading) {
        true -> {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeLoadingShimmer()
                HomeLoadingShimmer()
                HomeLoadingShimmer()
            }
        }
        false -> {
            if(!isConnected)
                ConnectionWarning(
                    modifier = Modifier.fillMaxSize(),
                    warnTitle = "Internet Anda Telah Teputus !",
                    warnText = "Silahkan untuk memeriksa koneksi internet anda dan coba untuk refresh kembali halaman ini",
                )
            
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                GroupDataSection(homeState) { groupId, passDataGroupId ->
                    navigateToGrupDtl(groupId, passDataGroupId)
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                if(homeState.passDataList.isNotEmpty() && isConnected && !homeState.isLoading){
                    UserPassDataSection(homeState.passDataList,homeState.totalDataPass ?: 0,dataPass,sheetState,navigateToListUserPass)
                }

                if(homeState.passDataList.isEmpty() && !homeState.isLoading && isConnected)
                    EmptyWarning(
                        modifier = Modifier.fillMaxSize(),
                        warnTitle = "Anda Belum Memiliki Data Password",
                        warnText = "Silahkan Tambahkan Data Password Anda melalui Tombol Dibawah",
                        btnTxt = "Tambahkan Password",
                        isEnableBtn = true,
                        onSelect = {
                            navigateToFormPass()
                        }
                    )
            }



        }

        else -> {}
    }
}

@Composable
fun UserDataPassHolder(dataPass: DataPass, sheetState: ModalBottomSheetState, dataParse: MutableState<DataPass?>) {
    val scope = rememberCoroutineScope()
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
                    dataPass.accountName,
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Text(
                    dataPass.email ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    color = secondaryColor
                )
            }
            IconButton(
                content = {
                    Image( painterResource(Res.drawable.menu_ic), "")
                },
                onClick = {
                    scope.launch {
                        dataParse.value = DataPass(
                            accountName = dataPass.accountName,
                            email = dataPass.email,
                            id = dataPass.id,
                            jenisData = dataPass.jenisData,
                            password = dataPass.password,
                            url = dataPass.url,
                            username = dataPass.username,
                            isEncrypted = dataPass.isEncrypted
                        )
                        sheetState.show()
                    }
                }
            )
        }
    }
    Spacer(
        modifier = Modifier.height(11.dp)
    )
}