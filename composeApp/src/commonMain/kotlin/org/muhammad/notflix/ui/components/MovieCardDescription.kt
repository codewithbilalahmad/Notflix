package org.muhammad.notflix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.kmpalette.loader.NetworkLoader
import com.kmpalette.rememberDominantColorState
import io.ktor.http.Url
import org.muhammad.notflix.util.DispatcherProvider
import org.muhammad.notflix.util.loadImage
import orgmuhammadnotflix.FavoriteMovies

@Composable
fun MovieCardDescription(
    modifier: Modifier = Modifier,
    movie: FavoriteMovies, onItemClick: (Int) -> Unit,
    networkLoader: NetworkLoader,
) {
    val dominantColorState = rememberDominantColorState(
        loader = networkLoader,
        defaultColor = Color.DarkGray,
        defaultOnColor = Color.LightGray,
        coroutineContext = DispatcherProvider.io
    )
    val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
    imageUrl.let { img ->
        LaunchedEffect(img) {
            dominantColorState.updateFrom(Url(img))
        }
    }
    Card(modifier = modifier.clickable { onItemClick(movie.id.toInt()) }) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                painter = rememberAsyncImagePainter(imageUrl),
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    modifier = Modifier,
                    text = movie.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp),
                    textAlign = TextAlign.Start,
                    color = dominantColorState.onColor,
                    lineHeight = 30.sp
                )
                ExpandedText(modifier = Modifier.padding(bottom = 4.dp), text = movie.overview)
            }
        }
    }
}