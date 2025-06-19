package org.muhammad.notflix.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.unknown_movie
import org.jetbrains.compose.resources.stringResource
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails
import org.muhammad.notflix.util.loadImage

@Composable
fun MovieCardPortraitCompact(
    modifier: Modifier = Modifier,
    movie: Movie,
    onItemClick: (Int) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Card(
            modifier = Modifier.width(150.dp).fillMaxHeight().clickable { onItemClick(movie.id) },
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(220.dp).sizeIn(minHeight = 30.dp),
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Text(
            modifier = Modifier.width(145.dp),
            text = movie.title ?: stringResource(Res.string.unknown_movie) ,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}