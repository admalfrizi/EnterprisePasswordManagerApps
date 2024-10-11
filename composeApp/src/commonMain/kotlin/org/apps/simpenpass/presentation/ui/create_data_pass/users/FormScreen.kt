package org.apps.simpenpass.presentation.ui.create_data_pass.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.presentation.components.formComponents.BtnForm
import org.apps.simpenpass.style.secondaryColor
import org.apps.simpenpass.utils.popUpLoading
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FormScreen(
    navController: NavController,
    formViewModel: FormViewModel = koinViewModel()
) {
    val isDismiss = remember { mutableStateOf(true) }
    val isLoading = remember { mutableStateOf(false) }

    if(isLoading.value){
        popUpLoading(isDismiss)
    }

    Scaffold(
        bottomBar = {
            BtnForm({
                isLoading.value = true
                navController.navigateUp()
            },{ navController.navigateUp() },Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(secondaryColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )
        },
        content = {
            Box(
                modifier = Modifier.padding(it).fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    FormContentView()
                }
            }
        }
    )
}

