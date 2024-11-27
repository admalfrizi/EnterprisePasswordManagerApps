package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    @SerialName("otp")
    val otp: Int,
    @SerialName("isResetPass")
    val isResetPass: Boolean,
)
