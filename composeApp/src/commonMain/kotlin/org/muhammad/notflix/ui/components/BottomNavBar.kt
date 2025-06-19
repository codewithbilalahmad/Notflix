package org.muhammad.notflix.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.theme.Gray

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    bottomNavItems: List<NavigationItem>,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface.copy(0.85f)
    ) {
        bottomNavItems.iterator().forEach { screen ->
            val backStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry?.destination?.route
            val isSelected = screen.route == currentDestination
            NavigationBarItem(
                icon = {
                    val icon = if (isSelected) screen.selectedIcon else screen.unSelectedIcon
                    icon?.let { Icon(imageVector = it, contentDescription = null) }
                },
                label = {
                    Text(text = screen.title, style = MaterialTheme.typography.labelSmall)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Gray
                ),
                selected = isSelected,
                onClick = {
                    if (screen.route != currentDestination) {
                        navHostController.navigate(screen.route) {
                            navHostController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                })
        }
    }
}