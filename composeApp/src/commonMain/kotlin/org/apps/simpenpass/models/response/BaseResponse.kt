package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<out T>(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code : Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T? = null,
)
