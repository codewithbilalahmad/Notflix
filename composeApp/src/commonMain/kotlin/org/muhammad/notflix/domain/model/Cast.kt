package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    @SerialName("actor")
    val actor : List<Actor>?=null,
    @SerialName("id")
    val id : Int
)
