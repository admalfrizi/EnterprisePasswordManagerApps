package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAdminMemberGroupRequest(
    @SerialName("member_id")
    val memberId : Int,
    @SerialName("is_group_admin")
    var isGroupAdmin: Boolean,
)
