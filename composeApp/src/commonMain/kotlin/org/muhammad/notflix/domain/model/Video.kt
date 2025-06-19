package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("id")
    val id: String?,
    @SerialName("iso31661")
    val iso31661: String?,
    @SerialName("iso6391")
    val iso6391: String?,
    @SerialName("key")

    val key: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("official")
    val official: Boolean?,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("site")

    val site: String?,
    @SerialName("size")

    val size: Int?,
    @SerialName("type")
    val type: String?
)
