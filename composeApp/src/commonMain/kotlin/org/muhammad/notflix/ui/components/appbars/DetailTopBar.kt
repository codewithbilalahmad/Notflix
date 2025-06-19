package org.muhammad.notflix.ui.components.appbars

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kmpalette.loader.NetworkLoader
import com.kmpalette.rememberDominantColorState
import io.ktor.http.Url
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.unknown_movie
import org.jetbrains.compose.resources.stringResource
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.ui.viewModel.MovieViewModel
import org.muhammad.notflix.util.DispatcherProvider
import org.muhammad.notflix.util.getMovieDuration
import org.muhammad.notflix.util.loadImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
    networkLoader: NetworkLoader,
    onNavigationClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: (MovieDetails?, Boolean?) -> Unit,
    movieViewModel: MovieViewModel,
) {
    val dominantColorState = rememberDominantColorState(
        loader = networkLoader,
        defaultColor = Color.DarkGray,
        defaultOnColor = Color.LightGray,
        coroutineContext = DispatcherProvider.io
    )
    movieDetails.posterPath?.loadImage()?.let { img ->
        LaunchedEffect(img) {
            dominantColorState.updateFrom(Url(img))
        }
    }
    var isFavouriteMovie = movieViewModel.isMovieFavourite(movieDetails.id.toLong())
    val backgroundColor by animateColorAsState(targetValue = MaterialTheme.colorScheme.surface)
    Box(modifier = modifier.fillMaxWidth().height(350.dp)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            model = movieDetails.backdropPath?.loadImage(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Box(
            modifier = Modifier.fillMaxWidth().height(210.dp).align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(listOf(Color.Transparent, dominantColorState.color))
                )
        )
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp).align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movieDetails.title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 32.sp),
                maxLines = 2, color = dominantColorState.onColor,
                overflow = TextOverflow.Ellipsis, lineHeight = 30.sp
            )
            movieDetails.createdAt.getMovieDuration()?.let {
                Text(
                    modifier = Modifier,
                    text = it,
                    color = dominantColorState.onColor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp
                )
            }
        }
        TopAppBar(modifier = Modifier.fillMaxWidth(),title = {
            Text(
                modifier = Modifier,
                text = movieDetails.title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }, navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, actions = {
            IconButton(onClick = { onShareClick() }) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = {
                isFavouriteMovie.let { fav -> isFavouriteMovie = !fav }
                onFavoriteClick(movieDetails, isFavouriteMovie)
            }) {
                val icon = if (isFavouriteMovie) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
                val tint = if(isFavouriteMovie) Color.Red else MaterialTheme.colorScheme.onSurface
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint
                )
            }
        },colors =TopAppBarDefaults.mediumTopAppBarColors(containerColor = backgroundColor))
    }
}