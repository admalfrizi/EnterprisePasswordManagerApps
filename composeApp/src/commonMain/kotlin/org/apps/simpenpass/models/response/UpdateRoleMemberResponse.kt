package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRoleMemberResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("posisi_id")
    val posisiId: Int?,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String
)
