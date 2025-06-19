package org.muhammad.notflix.util

import kotlinx.datetime.toLocalDate


fun String.capitalizeEachWork() : String{
    return lowercase().split(" ").joinToString(" "){firstCharacter ->
        firstCharacter.replaceFirstChar {
            if(it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}

fun String.getReleaseDate(): String {
    val localDate = this.toLocalDate()
    return "${localDate.dayOfMonth} ${localDate.month}, ${localDate.year}"
}

fun Int?.getMovieDuration(): String? {
    return if (this != null) {
        val hours = (this / 60)
        val minutes = this % 60
        val runtime = if (hours <= 1) "${hours}hr ${minutes}mins" else "${hours}hrs ${minutes}mins"
        runtime
    } else null
}

fun Double.getPopularity(): String {
    return ((this.toInt() * 100) * 10).toString()
}

fun Double.getRaking(): String {
    val byTwo = this / 2
    val before = byTwo.toString().substringBefore(".")
    val after = byTwo.toString().substringAfter(".").split("")[1]
    return "$before.$after"
}

fun Int.toBoolean(): Boolean {
    return this != 0
}