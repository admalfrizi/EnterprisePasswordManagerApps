package org.apps.simpenpass.presentation.components.homeComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.arrow_right_ic

@Composable
fun MostUsedSection(
    sheetState: ModalBottomSheetState
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Yang Sering Dipakai",
                style = MaterialTheme.typography.body2,
                color = secondaryColor
            )
            Image(
                painterResource(Res.drawable.arrow_right_ic),
                ""
            )
        }
        Spacer(
            modifier = Modifier.height(11.dp)
        )
//        DataPassHolder("Nama Akun", "Email",sheetState)
//        Spacer(
//            modifier = Modifier.height(11.dp)
//        )
//        DataPassHolder("Nama Akun", "Email",sheetState)
//        Spacer(
//            modifier = Modifier.height(11.dp)
//        )
//        DataPassHolder("Nama Akun", "Email", sheetState)
//        Spacer(
//            modifier = Modifier.height(11.dp)
//        )
    }
}