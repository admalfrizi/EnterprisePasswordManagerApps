package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserDataRequest(
    @SerialName("nama")
    val nama: String,
    @SerialName("email")
    val email: String
)
