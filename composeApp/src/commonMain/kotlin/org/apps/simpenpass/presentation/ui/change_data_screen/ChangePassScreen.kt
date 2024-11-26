package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.style.secondaryColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChangePassScreen(
    token: String,
    navToBack : () -> Unit,
    changeDataViewModel: ChangeDataViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).imePadding().fillMaxSize(),
        backgroundColor = secondaryColor,
        topBar = {
            TopAppBar(
                backgroundColor = secondaryColor,
                title = {

                },
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navToBack()
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
            )
        }
    ){
        Column(
            modifier = Modifier.fillMaxWidth().imePadding()
        ){
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Silahkan Reset Password",
                style = MaterialTheme.typography.h1,
                fontSize = 32.sp
            )
            Spacer(
                modifier = Modifier.height(35.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                text = "Masukan password baru untuk melakukan pembaharuan password pada akun anda",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
        }
    }
}