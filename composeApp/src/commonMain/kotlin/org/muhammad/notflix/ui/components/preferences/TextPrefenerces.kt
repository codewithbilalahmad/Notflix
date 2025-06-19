package org.muhammad.notflix.ui.components.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextPreferences(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    title: String,
    subTitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let { ico ->
            Icon(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp).weight(2f),
                imageVector = ico,
                contentDescription = null
            )
        }
        Column(Modifier.height(65.dp).weight(8f), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subTitle.isNotEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp),
                    text = subTitle,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis, maxLines = 1
                )
            }
        }
    }
}