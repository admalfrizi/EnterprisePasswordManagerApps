package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRoleReponse(
    @SerialName("group_id")
    val groupId: String,
    @SerialName("nm_posisi")
    val nmPosisi: String,
)
