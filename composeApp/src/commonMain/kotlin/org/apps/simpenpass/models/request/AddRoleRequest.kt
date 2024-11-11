package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRoleRequest(
    @SerialName("nm_posisi")
    val nmPosisi: String
)
