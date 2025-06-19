package org.muhammad.notflix.ui.screens.favoruites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kmpalette.loader.rememberNetworkLoader
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.title_favorites
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.muhammad.notflix.domain.util.Result
import org.muhammad.notflix.ui.components.ErrorScreen
import org.muhammad.notflix.ui.components.LoadingSection
import org.muhammad.notflix.ui.components.MovieCardDescription
import org.muhammad.notflix.ui.components.appbars.AppBar
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.viewModel.MovieViewModel

@Composable
fun FavoriteScreen(navHostController: NavHostController, viewModel: MovieViewModel = koinInject()) {
    val favoriteMoviesState by viewModel.favoriteMovies.collectAsState()
    val networkLoader = rememberNetworkLoader(httpClient = koinInject())
    LaunchedEffect(Unit){
        viewModel.getFavoriteMovies()
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBar(stringResource(Res.string.title_favorites))
    }) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (favoriteMoviesState) {
                is Result.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                        viewModel.getFavoriteMovies()
                    })
                }
                Result.Loading -> {
                    LoadingSection(modifier = Modifier.fillMaxWidth())
                }
                is Result.Success -> {
                    val response =
                        (favoriteMoviesState as? Result.Success)?.response?.toMutableList()
                            ?: emptyList()
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(response) { favoriteMov ->
                            MovieCardDescription(
                                modifier = Modifier.fillMaxWidth().height(260.dp)
                                    .padding(vertical = 4.dp),
                                movie = favoriteMov,
                                networkLoader = networkLoader, onItemClick = {movieId ->
                                    navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}