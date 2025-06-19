package org.muhammad.notflix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.unknown_actor
import org.jetbrains.compose.resources.stringResource
import org.muhammad.notflix.domain.model.Actor
import org.muhammad.notflix.ui.theme.TextSecondary
import org.muhammad.notflix.util.loadImage

@Composable
fun MovieCastItem(modifier: Modifier = Modifier, actor: Actor) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp).clip(CircleShape),
            model = "https://image.tmdb.org/t/p/w500/${actor.profilePath}",
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.width(78.dp),
            text = actor.name ?: stringResource(Res.string.unknown_actor),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.width(77.dp),
            text = actor.character ?: "Unknown Character",
            style = MaterialTheme.typography.labelSmall,
            fontSize = 12.sp,
            color = TextSecondary,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}