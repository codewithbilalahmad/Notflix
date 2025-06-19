package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("backdropPath")

    val backdropPath: String? = null,
    @SerialName("id")

    val id: Int,
    @SerialName("originalLanguage")

    val originalLanguage: String? = null,
    @SerialName("originalTitle")

    val originalTitle: String? = null,
    @SerialName("overview")

    val overview: String? = null,
    @SerialName("popularity")

    val popularity: Double? = null,
    @SerialName("posterPath")

    val posterPath: String? = null,
    @SerialName("releaseDate")

    val releaseDate: String? = null,
    @SerialName("title")

    val title: String? = null,
    @SerialName("video")
    val video: Boolean? = null,
    @SerialName("voteAverage")

    val voteAverage: Double? = null,
    @SerialName("voteCount")

    val voteCount: Int? = null,
    @SerialName("category")

    val category: String? = null,
    @SerialName("isFavorite")

    val isFavorite: Boolean? = null,
    @SerialName("cacheId")

    val cacheId: Int? = 0,
    @SerialName("mediaType")

    val mediaType: String? = null,
)

fun Movie.toDomain(): MovieDetails {
    return MovieDetails(
        adult = this.adult,
        backdropPath = this.backdropPath,
        homepage = this.title,
        id = this.id,
        originalTitle = this.originalTitle.toString(),
        originalLanguage = this.originalLanguage.toString(),
        video = this.video,
        createdAt = this.releaseDate?.toInt(),
        popularity = this.popularity,
        tagline = this.mediaType,
        status = this.title,
        imdbId = this.title,
        overview = this.overview.toString(),
        title = this.title.toString()
    )
}
