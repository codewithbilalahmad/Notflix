package org.muhammad.notflix.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kmpalette.loader.rememberNetworkLoader
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.cast
import notflix.composeapp.generated.resources.overview
import notflix.composeapp.generated.resources.similar_movies
import notflix.composeapp.generated.resources.unknown_actor
import notflix.composeapp.generated.resources.unknown_movie
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.muhammad.notflix.domain.model.toDomain
import org.muhammad.notflix.domain.util.Result
import org.muhammad.notflix.ui.components.ErrorScreen
import org.muhammad.notflix.ui.components.LoadingSection
import org.muhammad.notflix.ui.components.MovieCardPortrait
import org.muhammad.notflix.ui.components.MovieCastItem
import org.muhammad.notflix.ui.components.MovieRakingSection
import org.muhammad.notflix.ui.components.appbars.DetailTopBar
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.viewModel.MovieViewModel
import org.muhammad.notflix.util.getPopularity
import org.muhammad.notflix.util.getRaking

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    viewModel: MovieViewModel = koinInject(), movieId: Int,
) {
    LaunchedEffect(Unit) {
        viewModel.getDetailMovie(movieId)
        viewModel.getSimilarMovies(movieId)
        viewModel.getMovieCast(movieId)
        viewModel.isMovieFavourite(movieId.toLong())
    }
    val scrollState = rememberScrollState()
    val movieDetailState by viewModel.movieDetail.collectAsState()
    val similarMoviesState by viewModel.similarMovies.collectAsState()
    val movieCastState by viewModel.movieCast.collectAsState()
    val networkLoader = rememberNetworkLoader()
    when (movieDetailState) {
        is Result.Error -> {
            ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                viewModel.getDetailMovie(movieId)
            })
        }
        Result.Loading -> {
            LoadingSection(modifier = Modifier.fillMaxWidth())
        }
        is Result.Success -> {
            val response = (movieDetailState as? Result.Success)?.response
            response?.let { resp ->
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    DetailTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        movieDetails = resp,
                        onNavigationClick = {
                            navHostController.navigateUp()
                        }, onFavoriteClick = { movieDetails, isFavorite ->
                            if (isFavorite == true) movieDetails?.let { it1 ->
                                viewModel.saveFavoriteMovie(movieDetails = it1)
                            } else movieDetails?.id?.toLong()
                                ?.let { it1 ->
                                    viewModel.deleteFavoriteMovie(
                                        it1
                                    )
                                }
                        }, onShareClick = {

                        }, networkLoader = networkLoader, movieViewModel = viewModel
                    )
                }) { innerPadding ->
                    Column(
                        Modifier.fillMaxSize().padding(innerPadding).verticalScroll(scrollState)
                    ) {
                        resp.voteAverage?.let { vote ->
                            MovieRakingSection(
                                popularity = vote.getPopularity(),
                                voteAverage = vote.getRaking()
                            )
                        }
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = stringResource(
                                Res.string.overview
                            ),
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            text = resp.overview ?: stringResource(Res.string.unknown_movie),
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp),
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis
                        )
                        when (movieCastState) {
                            is Result.Error -> {
                                ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                                    viewModel.getMovieCast(movieId)
                                })
                            }
                            Result.Loading -> {
                                LoadingSection(modifier = Modifier.fillMaxWidth())
                            }
                            is Result.Success -> {
                                val movieCast = (movieCastState as? Result.Success)?.response
                                Text(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    text = stringResource(Res.string.cast),
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                                )

                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    movieCast?.let { movCast ->
                                        items(movCast) { ac ->
                                            MovieCastItem(modifier = Modifier, actor = ac)
                                        }
                                    }
                                }
                            }
                        }
                        when (similarMoviesState) {
                            is Result.Error -> {
                                ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                                    viewModel.getSimilarMovies(movieId)
                                })
                            }
                            Result.Loading -> {
                                LoadingSection(modifier = Modifier.fillMaxWidth())

                            }
                            is Result.Success -> {
                                val similarMovies = (similarMoviesState as? Result.Success)?.response?.results?.toMutableList() ?: emptyList()
                                Text(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    text = stringResource(Res.string.similar_movies),
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    itemsIndexed(similarMovies){index , simiMov ->
                                        MovieCardPortrait(movie = simiMov, onItemClick = {movieId ->
                                            navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
                                        })
                                        if(index == similarMovies.lastIndex){
                                            LaunchedEffect(Unit){
                                                viewModel.loadMoreSimilarMovies()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}