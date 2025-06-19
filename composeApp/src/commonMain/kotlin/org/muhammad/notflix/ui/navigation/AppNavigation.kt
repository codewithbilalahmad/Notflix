package org.muhammad.notflix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.muhammad.notflix.ui.screens.details.DetailScreen
import org.muhammad.notflix.ui.screens.favoruites.FavoriteScreen
import org.muhammad.notflix.ui.screens.home.HomeScreen
import org.muhammad.notflix.ui.screens.search.SearchScreen
import org.muhammad.notflix.ui.screens.settings.SettingsScreen
import org.muhammad.notflix.ui.viewModel.MovieViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    isCompact: Boolean,
    viewModel: MovieViewModel,
) {
    NavHost(navController = navHostController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navHostController = navHostController, viewModel = viewModel)
        }
        composable(NavigationItem.Favourite.route){
            FavoriteScreen(navHostController = navHostController, viewModel = viewModel)
        }
        composable(NavigationItem.Settings.route){
            SettingsScreen(viewModel = viewModel)
        }
        composable(NavigationItem.Search.route){
            SearchScreen(navHostController = navHostController,viewModel, isCompact)
        }
        composable("${NavigationItem.Detail.route}/{movieId}", arguments = listOf(
            navArgument("movieId"){
                type = NavType.IntType
            }
        )){
            val movieId = it.arguments?.getInt("movieId") ?: 0
            DetailScreen(navHostController, viewModel, movieId)
        }
    }
}