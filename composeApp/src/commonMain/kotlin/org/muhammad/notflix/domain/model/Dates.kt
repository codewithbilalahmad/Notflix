package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dates(
    @SerialName("minimum")
    val minimum : String?,
    @SerialName("maximum")
    val maximum : String?
)