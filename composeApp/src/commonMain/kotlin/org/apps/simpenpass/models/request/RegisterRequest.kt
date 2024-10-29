package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email : String,
    @SerialName("password")
    val password: String,
    @SerialName("password_confirmation")
    val cPassword: String,
)
