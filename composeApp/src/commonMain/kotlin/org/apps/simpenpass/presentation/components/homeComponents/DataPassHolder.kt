package org.apps.simpenpass.presentation.components.homeComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.menu_ic

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataPassHolder(accName: String,email : String,sheetState: ModalBottomSheetState) {
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
                    accName,
                    style = MaterialTheme.typography.body1,
                    color = secondaryColor
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Text(
                    email,
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
                        sheetState.show()
                    }
                }
            )
        }
    }
}