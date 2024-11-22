package org.apps.simpenpass.presentation.components.groupDtlComponents

import androidx.compose.foundation.Image
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.apps.simpenpass.presentation.ui.group_pass.GroupDetailsViewModel
import org.apps.simpenpass.style.secondaryColor
import org.jetbrains.compose.resources.painterResource
import resources.Res
import resources.menu_ic

@Composable
fun TopBarDtl(
    navBack: () -> Unit,
    navToGroupSettings: () -> Unit,
    groupState: GroupDetailsViewModel
) {
    var isDropdownShow by remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = secondaryColor,
        title = {
            Text(
                "Detail Grup"
            )
        },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    navBack()
                    groupState.clearState()
                },
                content = {
                    Image(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
        },
        actions = {
            IconButton(
                onClick = {
                    isDropdownShow = true
                },
                content = {
                    Image(
                        painterResource(Res.drawable.menu_ic),
                        "",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
            DropdownMenu(
                expanded = isDropdownShow,
                onDismissRequest = { isDropdownShow = false }
            ) {
                DropdownMenuItem(
                    content = {
                        Text(text = "Pengaturan Grup")
                    },
                    onClick = {
                        navToGroupSettings()
                        isDropdownShow = false
                    }
                )
            }
        }
    )
}

