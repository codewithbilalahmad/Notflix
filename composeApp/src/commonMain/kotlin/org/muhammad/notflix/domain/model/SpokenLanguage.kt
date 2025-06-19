package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    @SerialName("englishName")
    val englishName: String? = null,
    @SerialName("iso6391")

    val iso6391: String? = null,
    @SerialName("name")

    val name: String? = null
)
