package org.muhammad.notflix.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.muhammad.notflix.ui.theme.Golden

@Composable
fun RakingBar(raking: Int,  modifier: Modifier = Modifier) {
    val totalRakingStars = 6
    val correctRakingValue = if (raking > 6) totalRakingStars else raking
    var selectedRaking by remember { mutableStateOf(correctRakingValue) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..totalRakingStars) {
            val icon = if (i <= selectedRaking) Icons.Filled.Star else Icons.Filled.StarBorder
            val tint = if (i <= selectedRaking) Golden else Color.LightGray
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = modifier.size(24.dp).clickable {
                    selectedRaking = i
                })
        }
    }
}