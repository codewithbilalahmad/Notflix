package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResults(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("movies")

    val movies: List<Movie>? = null,
    @SerialName("totalPages")

    val totalPages: Int? = null,
    @SerialName("totalResults")

    val totalResults: Int? = null
)
