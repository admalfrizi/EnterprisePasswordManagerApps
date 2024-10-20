package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    @SerialName("token_otp")
    val tokenOtp: String,
    @SerialName("email")
    val email: String
)