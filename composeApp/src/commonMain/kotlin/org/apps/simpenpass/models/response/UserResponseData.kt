package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apps.simpenpass.models.UserData

@Serializable
data class UserResponseData(
    @SerialName("user")
    val user: UserData,
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("token_type")
    val token_type: String,
)