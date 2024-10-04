package org.apps.simpenpass.screen

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import resources.Res
import resources.dashboard_ic
import resources.group_ic
import resources.ic_profile

sealed class BottomNavMenuData(
    var route: String,
    var title: String,
    val defaultIcon : DrawableResource
) {
    data object Home : BottomNavMenuData(
        route = Screen.Home.route,
        title = "Dashboard",
        defaultIcon = Res.drawable.dashboard_ic
    )

    data object Group : BottomNavMenuData(
        route = Screen.Group.route,
        title = "Grup",
        defaultIcon = Res.drawable.group_ic
    )

    data object Profile : BottomNavMenuData(
        route = Screen.Profile.route,
        title = "Profile",
        defaultIcon = Res.drawable.ic_profile
    )
}