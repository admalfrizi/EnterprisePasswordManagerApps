package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPassRequest(
    @SerialName("token")
    val token: String,
    @SerialName("password")
    val password: String,
)
