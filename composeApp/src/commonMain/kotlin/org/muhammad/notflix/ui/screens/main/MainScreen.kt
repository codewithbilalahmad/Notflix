package org.muhammad.notflix.ui.screens.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.muhammad.notflix.ui.components.BottomNavBar
import org.muhammad.notflix.ui.components.NavRailBar
import org.muhammad.notflix.ui.navigation.AppNavigation
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.theme.NotflixTheme
import org.muhammad.notflix.ui.viewModel.MovieViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(viewModel: MovieViewModel) {
    val windowSizeClass = calculateWindowSizeClass()
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val isDarkTheme = viewModel.themePrefernces.value == 1
    val navHostController = rememberNavController()
    val navItem = listOf(
        NavigationItem.Home,
        NavigationItem.Search,
        NavigationItem.Favourite,
        NavigationItem.Settings,
    )
    NotflixTheme(darkTheme = isDarkTheme) {
        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
            if (isCompact) {
                BottomNavBar(bottomNavItems = navItem, navHostController = navHostController)
            }
        }) {innerPadding ->
            Row(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                if (!isCompact) {
                    NavRailBar(navigationItems = navItem, navHostController = navHostController)
                }
                AppNavigation(
                    navHostController = navHostController,
                    viewModel = viewModel,
                    isCompact = isCompact
                )
            }
        }
    }
}