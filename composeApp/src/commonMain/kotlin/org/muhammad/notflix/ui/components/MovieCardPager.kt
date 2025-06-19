package org.muhammad.notflix.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kmpalette.loader.NetworkLoader
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import io.ktor.http.Url
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.unknown_movie
import org.jetbrains.compose.resources.stringResource
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.util.DispatcherProvider
import org.muhammad.notflix.util.loadImage

@Composable
fun MovieCardPager(
    modifier: Modifier = Modifier,
    movie: Movie,
    networkLoader: NetworkLoader = rememberNetworkLoader(),
    onItemClick: (Int) -> Unit,
) {
    val dominantColorState = rememberDominantColorState(
        loader = networkLoader,
        defaultColor = Color.DarkGray,
        defaultOnColor = Color.LightGray,
        coroutineContext = DispatcherProvider.io
    )
    movie.backdropPath?.loadImage()?.let { img ->
        LaunchedEffect(img) {
            dominantColorState.updateFrom(Url(img))
        }
    }
    Card(modifier = modifier.clickable { onItemClick(movie.id) }) {
        Box {
            AsyncImage(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(190.dp).align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(listOf(Color.Transparent, dominantColorState.color))
                    )
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = movie.title ?: stringResource(Res.string.unknown_movie),
                    fontSize = 28.sp,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = dominantColorState.onColor,
                    lineHeight = 30.sp
                )
                movie.voteAverage?.let { vote ->
                    RakingBar(modifier = Modifier, raking = vote.toInt())
                }
            }
        }
    }
}

@Composable
fun MovieCardPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorSize: Dp = 6.dp,
    spacing: Dp = 6.dp,
    inActiveColor: Color = Color.LightGray,
    activeColor: Color = Color.DarkGray,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally)
    ) {
        repeat(pagerState.pageCount) { currentPage ->
            Canvas(modifier = Modifier.size(indicatorSize), onDraw = {
                drawCircle(color = if (pagerState.currentPage == currentPage) activeColor else inActiveColor)
            })
        }
    }
}