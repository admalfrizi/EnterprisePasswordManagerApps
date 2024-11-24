package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAdminMemberResponse(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("is_group_admin")
    val isGroupAdmin: Int
)
