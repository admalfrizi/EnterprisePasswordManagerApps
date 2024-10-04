package org.apps.simpenpass.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.apps.simpenpass.screen.BottomNavMenuData
import org.apps.simpenpass.screen.Screen
import org.apps.simpenpass.style.fontColor1
import org.apps.simpenpass.style.primaryColor
import org.jetbrains.compose.resources.vectorResource
import resources.Res
import resources.dashboard_ic
import resources.group_ic
import resources.ic_profile

@Composable
fun BottomNavigationBar(navController: NavController, routeNav: List<BottomNavMenuData>) {
    BottomNavigation(
        backgroundColor = primaryColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        routeNav.forEach { itemNav ->
            val selected = currentDestination?.hierarchy?.any { it.route == itemNav.route } == true
            BottomNavigationItem(
                icon = {
                    Icon(
                        vectorResource(itemNav.defaultIcon),
                        contentDescription = "",
                        tint = if(selected) Color(0xFFFEC20F) else fontColor1
                    )},
                label = { Text(
                    text = itemNav.title,
                    color = if(selected) Color(0xFFFEC20F) else fontColor1,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 9.sp
                )},
                selected = selected,
                onClick = {
                    navController.navigate(itemNav.route) {
                        navController.graph.findStartDestination().route?.let {
                            popUpTo(it){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }

    }
}