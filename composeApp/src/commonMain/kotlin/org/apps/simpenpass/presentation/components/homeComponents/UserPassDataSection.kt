package org.apps.simpenpass.presentation.components.homeComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.secondaryColor

@Composable
fun UserPassDataSection(
    listData : List<PassResponseData>,
    sheetState: ModalBottomSheetState,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Data Anda",
            style = MaterialTheme.typography.body2,
            color = secondaryColor
        )
        Spacer(
            modifier = Modifier.height(11.dp)
        )
//        DataPassHolder("Nama Akun", "Email", sheetState)
//        Spacer(
//            modifier = Modifier.height(11.dp)
//        )
//        DataPassHolder("Nama Akun", "Email", sheetState)
//        Spacer(
//            modifier = Modifier.height(11.dp)
//        )
//        DataPassHolder("Nama Akun", "Email", sheetState)
//        Spacer(
//            modifier = Modifier.height(7.dp)
//        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listData) { data ->
                DataPassHolder(data.accountName!!, data.email!!, sheetState)
            }
        }

        if(listData.size > 3) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                border = BorderStroke(color = Color(0xFF8CC4FB), width = 1.dp),
                onClick = {navController.navigate(Screen.ListPassDataUser.route)},
                content = {
                    Text(
                        "Lihat Lebih Banyak",
                        style = MaterialTheme.typography.subtitle2,
                        color = secondaryColor
                    )
                }
            )
        }

    }
}