package org.muhammad.notflix.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle

private const val MINIMIZED_MAX_LINES = 3

@Composable
fun ExpandedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var clickable by remember { mutableStateOf(false) }
    var finalText by mutableStateOf(text)
    val endOfTitle = if (isExpanded) "Show Less" else "Read More"
    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect
        when {
            isExpanded -> {
                finalText = "$text $endOfTitle"
            }

            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(lineIndex = MINIMIZED_MAX_LINES - 1)
                val showMoreString = "... $endOfTitle"
                val adjustedText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length).dropLastWhile { it == ',' || it == '.' }
                finalText = "$adjustedText$showMoreString"
                clickable = true
            }
        }
    }
    Text(
        text = finalText,
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        style = style,
        modifier = modifier.clickable(enabled = isExpanded) {
            isExpanded = !isExpanded
        }.animateContentSize()
    )
}