package org.apps.simpenpass.models.user_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String
)
