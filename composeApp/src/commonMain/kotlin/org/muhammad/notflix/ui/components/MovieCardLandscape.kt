package org.muhammad.notflix.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import io.ktor.http.Url
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.unknown_movie
import org.jetbrains.compose.resources.stringResource
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.util.DispatcherProvider
import org.muhammad.notflix.util.capitalizeEachWork
import org.muhammad.notflix.util.getReleaseDate

@Composable
fun MovieCardLandscape(
    modifier: Modifier = Modifier,
    movie: Movie,
    networkLoader: NetworkLoader = rememberNetworkLoader(),
    onClickItem: (Int) -> Unit,
) {
    val dominantColorState = rememberDominantColorState(
        loader = networkLoader,
        defaultColor = Color.DarkGray,
        defaultOnColor = Color.LightGray,
        coroutineContext = DispatcherProvider.io
    )
    val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}"
    imageUrl.let { img ->
        LaunchedEffect(img) {
            dominantColorState.updateFrom(Url(img))
        }
    }
    Card(
        modifier = modifier.clickable { onClickItem(movie.id) },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(modifier = modifier) {
            AsyncImage(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop, alignment = Alignment.Center
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp).background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, dominantColorState.color)
                    )
                ).align(Alignment.BottomCenter)
            )
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp).align(Alignment.BottomCenter)) {
                Text(
                    modifier = Modifier,
                    text = movie.title ?: stringResource(Res.string.unknown_movie) ,
                    fontSize = 18.sp,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = dominantColorState.onColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    movie.voteAverage?.let { RakingBar(raking = it.toInt(), modifier = Modifier) }
                    movie.releaseDate?.let { relDate ->
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 4.dp).width(1.dp)
                                .height(13.dp), color = dominantColorState.onColor
                        )
                        Text(
                            modifier = Modifier,
                            text = relDate.getReleaseDate().capitalizeEachWork(),
                            fontSize = 14.sp,
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            color = dominantColorState.onColor
                        )
                    }
                }
            }
        }
    }
}