package org.apps.simpenpass.presentation.components.homeComponents

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor
import resources.Res
import resources.group_ic
import resources.pass_data_ic

@Composable
fun HeaderContainer(
    nameUser: String?,
    totalUserDataPass: Int,
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(color = secondaryColor)
    ){
        Column {
            Spacer(
                modifier = Modifier.height(18.dp)
            )
            Text(
                "Selamat Datang, $nameUser",
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(start = 16.dp),
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                "Silahkan Amankan Kata Sandi Anda !",
                style = MaterialTheme.typography.subtitle1,
                color = fontColor1,
                modifier = Modifier.padding(start = 16.dp),
            )
            Spacer(
                modifier = Modifier.height(18.dp)
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                InfoContainer(
                    titleInfo = "Jumlah Data Password Anda",
                    vlData = totalUserDataPass,
                    bgColor = Color(0xFF1E559C),
                    iconInfo = Res.drawable.pass_data_ic
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                InfoContainer(
                    titleInfo = "Grup Yang Tergabung",
                    vlData = 10,
                    bgColor = Color(0xFF192E49) ,
                    iconInfo = Res.drawable.group_ic
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                InfoContainer(
                    titleInfo = "Total Data Password Grup",
                    vlData = 5,
                    bgColor = Color(0xFF1E559C),
                    iconInfo = Res.drawable.pass_data_ic
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
            }
            Spacer(
                modifier = Modifier.height(23.dp)
            )
        }

    }
}

