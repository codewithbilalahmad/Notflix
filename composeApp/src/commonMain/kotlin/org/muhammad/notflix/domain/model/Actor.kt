package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    @SerialName("castId")
    val castId: Int?,
    @SerialName("character")
    val character: String?,
    @SerialName("creditId")
    val creditId: String,
    @SerialName("name")
    val name: String?,
    @SerialName("originalName")
    val originalName: String?,
    @SerialName("profilePath")
    val profilePath: String?,
)