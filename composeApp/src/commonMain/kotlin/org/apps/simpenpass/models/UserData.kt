package org.apps.simpenpass.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    @SerialName("id")
    val id: Int,
    @SerialName("role")
    val role: String,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("updated_at")
    val updated_at: String,
)
