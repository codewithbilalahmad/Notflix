package org.muhammad.notflix.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kmpalette.loader.NetworkLoader
import com.kmpalette.loader.rememberNetworkLoader
import io.ktor.client.HttpClient
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.popular_movies
import notflix.composeapp.generated.resources.trending_movies
import notflix.composeapp.generated.resources.upcoming_movies
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.util.Result
import org.muhammad.notflix.ui.components.ErrorScreen
import org.muhammad.notflix.ui.components.LoadingSection
import org.muhammad.notflix.ui.components.MovieCardLandscape
import org.muhammad.notflix.ui.components.MovieCardPager
import org.muhammad.notflix.ui.components.MovieCardPagerIndicator
import org.muhammad.notflix.ui.components.MovieCardPortraitCompact
import org.muhammad.notflix.ui.components.SectionSeparator
import org.muhammad.notflix.ui.components.appbars.AppBar
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.theme.DarkPrimaryColor
import org.muhammad.notflix.ui.viewModel.MovieViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: MovieViewModel = koinInject<MovieViewModel>(),
) {
    val scrollState = rememberScrollState()
    val networkLoader = rememberNetworkLoader(httpClient = koinInject<HttpClient>())
    val trendingMoviesState by viewModel.trendingMovies.collectAsState()
    val upcomingMoviesState by viewModel.upComingMovies.collectAsState()
    val popularMoviesState by viewModel.popularMovies.collectAsState()
    val nowPlayingMoviesState by viewModel.nowPlayingMovies.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getTrendingMovies()
        viewModel.getPopularMovies()
        viewModel.getUpComingMovies(1)
        viewModel.getNowPlayingMovies()
    }
    Scaffold(topBar = {
        AppBar("Home")
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding).verticalScroll(scrollState)) {
            when (nowPlayingMoviesState) {
                is Result.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxWidth()) {
                        viewModel.getNowPlayingMovies()
                    }
                }

                Result.Loading -> {
                    LoadingSection(modifier = Modifier.fillMaxWidth())
                }

                is Result.Success -> {
                    val response =
                        (nowPlayingMoviesState as? Result.Success)?.response?.results?.toMutableList()
                            ?: emptyList()
                    NowPlayingMoviesSection(
                        response = response,
                        networkLoader = networkLoader,
                        navHostController
                    )
                }
            }
            when (trendingMoviesState) {
                is Result.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                        viewModel.getTrendingMovies()
                    })
                }

                Result.Loading -> {
                    LoadingSection(modifier = Modifier.fillMaxWidth())
                }

                is Result.Success -> {
                    val response =
                        (trendingMoviesState as? Result.Success)?.response?.results?.toMutableList()
                            ?: emptyList()
                    TrendingMoviesSection(
                        response = response,
                        navHostController = navHostController, viewModel = viewModel
                    )
                }
            }
            when (upcomingMoviesState) {
                is Result.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                        viewModel.getUpComingMovies(1)
                    })
                }

                Result.Loading -> {
                    LoadingSection(modifier = Modifier.fillMaxWidth())
                }

                is Result.Success -> {
                    val response =
                        (upcomingMoviesState as? Result.Success)?.response?.results?.toMutableList()
                            ?: emptyList()
                    UpComingMoviesSection(
                        response = response,
                        networkLoader,
                        navHostController = navHostController, viewModel = viewModel
                    )
                }
            }
            when (popularMoviesState) {
                is Result.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxWidth(), onRetry = {
                        viewModel.getPopularMovies()
                    })
                }

                Result.Loading -> {
                    LoadingSection(modifier = Modifier.fillMaxWidth())
                }

                is Result.Success -> {
                    val response =
                        (popularMoviesState as? Result.Success)?.response?.results?.toMutableList()
                            ?: emptyList()
                    PopularMoviesSection(response, navHostController, viewModel)
                }
            }
        }
    }

}

@Composable
fun UpComingMoviesSection(
    response: List<Movie>,
    networkLoader: NetworkLoader, viewModel: MovieViewModel,
    navHostController: NavHostController,
) {
    SectionSeparator(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        sectionTitle = stringResource(Res.string.upcoming_movies)
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.wrapContentHeight()
    ) {
        itemsIndexed(response) { index, item ->
            MovieCardLandscape(
                modifier = Modifier.width(300.dp).height(245.dp),
                movie = item,
                networkLoader = networkLoader, onClickItem = { movieId ->
                    navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
                }
            )
            if (index == response.lastIndex) {
                LaunchedEffect(Unit) {
                    viewModel.loadMoreUpcomingMovies()
                }
            }
        }
    }
}

@Composable
fun TrendingMoviesSection(
    response: List<Movie>,
    navHostController: NavHostController,
    viewModel: MovieViewModel,
) {
    SectionSeparator(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(), sectionTitle = stringResource(
            Res.string.trending_movies
        )
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(response) { index, item ->
            MovieCardPortraitCompact(movie = item, onItemClick = { movieId ->
                navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
            })
            if (index == response.lastIndex) {
                LaunchedEffect(Unit) {
                    viewModel.loadMoreTreadingMovies()
                }
            }
        }
    }
}

@Composable
fun PopularMoviesSection(response: List<Movie>, navHostController: NavHostController, viewModel: MovieViewModel) {
    SectionSeparator(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(), sectionTitle = stringResource(
            Res.string.popular_movies
        )
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(response) { index, item  ->
            MovieCardPortraitCompact(movie = item, onItemClick = { movieId ->
                navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
            })
            if(index == response.lastIndex){
                LaunchedEffect(Unit){
                    viewModel.loadMorePopularMovies()
                }
            }
        }
    }
}

@Composable
private fun NowPlayingMoviesSection(
    response: List<Movie>,
    networkLoader: NetworkLoader,
    navHostController: NavHostController,
) {
    val pagerState = rememberPagerState { response.size }
    HorizontalPager(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp), pageSpacing = 8.dp
    ) { currentPage ->
        MovieCardPager(
            modifier = Modifier.fillMaxWidth().height(280.dp),
            networkLoader = networkLoader,
            movie = response[currentPage], onItemClick = { movieId ->
                navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
            }
        )
        MovieCardPagerIndicator(
            modifier = Modifier.padding(vertical = 6.dp),
            pagerState = pagerState,
            indicatorSize = 6.dp,
            inActiveColor = Color.Gray,
            activeColor = DarkPrimaryColor
        )
    }
}