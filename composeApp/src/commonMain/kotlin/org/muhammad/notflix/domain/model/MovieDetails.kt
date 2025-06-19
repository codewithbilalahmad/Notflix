package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("backdropPath")
    val backdropPath: String? = null,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("imdbId")

    val imdbId: String? = null,
    @SerialName("originalLanguage")

    val originalLanguage: String? = null,
    @SerialName("originalTitle")
    val originalTitle: String?=null,
    @SerialName("overview")

    val overview: String,
    @SerialName("popularity")

    val popularity: Double? = null,
    @SerialName("posterPath")

    val posterPath: String? = null,
    @SerialName("releaseDate")

    val releaseDate: String? = null,
    @SerialName("runtime")

    val runtime: Int? = null,
    @SerialName("status")

    val status: String? = null,
    @SerialName("tagline")

    val tagline: String? = null,
    @SerialName("title")

    val title: String,
    @SerialName("video")
    val video: Boolean? = null,
    @SerialName("voteAverage")
    val voteAverage: Double? = null,
    @SerialName("voteCount")
    val voteCount: Int? = null,
    @SerialName("createdAt")
    val createdAt: Int? = null,
)
