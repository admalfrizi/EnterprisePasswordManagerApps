package org.apps.simpenpass.models.request

data class VerifyOtpRequest(
    val otp: Int,
    val email : String,
)
