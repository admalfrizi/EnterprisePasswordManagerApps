package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddMemberRequest(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("is_group_admin")
    val isGroupAdmin: Boolean
)
