package org.apps.simpenpass.presentation.components.formComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor

@Composable
fun HeaderContainer() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(color = secondaryColor)
    ){
        Column(
            modifier = Modifier.padding(bottom = 22.dp)
        ) {
            Spacer(
                modifier = Modifier.height(18.dp)
            )
            Text(
                "Amankan Password Anda !",
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(start = 16.dp),
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                "Silahkan masukan data password anda !",
                style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                color = fontColor1,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(
                modifier = Modifier.height(31.dp)
            )
            Text(
                "Untuk mencegah terjadinya kebocoran data, harap untuk tidak memberitahukan kata sandi akun anda kepada sembarang orang.",
                style = MaterialTheme.typography.subtitle1,
                color = fontColor1,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

    }
}

