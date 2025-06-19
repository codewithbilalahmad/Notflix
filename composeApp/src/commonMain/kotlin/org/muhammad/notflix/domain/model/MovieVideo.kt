package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieVideo(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("videos")

    val videos: List<Video>? = null
)
