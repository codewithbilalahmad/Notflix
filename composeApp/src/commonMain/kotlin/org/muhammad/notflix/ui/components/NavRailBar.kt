package org.muhammad.notflix.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.NavigationRailItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.theme.Gray
import org.muhammad.notflix.ui.theme.PrimaryColor

@Composable
fun NavRailBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    navigationItems: List<NavigationItem>,
) {
    NavigationRail(
        modifier = modifier.fillMaxHeight().alpha(0.95f),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = PrimaryColor
    ) {
        navigationItems.forEach { screen ->
            val backStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry?.destination?.route
            val isSelected = screen.route == currentDestination
            NavigationRailItem(icon = {
                val icon = if (isSelected) screen.selectedIcon else screen.unSelectedIcon
                icon?.let { Icon(imageVector = it, contentDescription = null) }
            }, label = {
                Text(text = screen.title, style = MaterialTheme.typography.labelSmall)
            }, selected = isSelected, onClick = {
                if (screen.route != currentDestination) {
                    navHostController.navigate(screen.route) {
                        navHostController.graph.startDestinationRoute?.let {route ->
                            popUpTo(route){
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }, selectedContentColor = PrimaryColor, unselectedContentColor = Gray)
        }
    }
}