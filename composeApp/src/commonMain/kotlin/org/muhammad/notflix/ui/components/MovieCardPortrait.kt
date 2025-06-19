package org.muhammad.notflix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.muhammad.notflix.domain.model.Movie
import org.muhammad.notflix.domain.model.MovieDetails

@Composable
fun MovieCardPortrait(
    modifier: Modifier = Modifier,
    movie: Movie,
    onItemClick: (Int) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(1.dp)) {
        MovieCardPortraitCompact(movie = movie, onItemClick = onItemClick)
        movie.voteAverage?.let { RakingBar(modifier = Modifier, raking = it.toInt()) }
    }
}