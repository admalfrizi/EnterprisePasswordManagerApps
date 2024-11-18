package org.apps.simpenpass.presentation.ui.group_pass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.secondaryColor

@Composable
fun PassDataDetailsScreen(
    navController: NavController
) {
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
            Column(
                modifier = Modifier.fillMaxWidth()
            ){

            }
        }
    )
}