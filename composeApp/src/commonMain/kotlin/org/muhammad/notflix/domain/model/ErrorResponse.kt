package org.muhammad.notflix.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("statusMessage")
    val statusMessage: String,
) : Exception()