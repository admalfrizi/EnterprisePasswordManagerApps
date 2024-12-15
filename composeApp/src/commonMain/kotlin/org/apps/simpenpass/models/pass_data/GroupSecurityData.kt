package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupSecurityData(
    @SerialName("id")
    val id: Int?,
    @SerialName("group_id")
    val groupId: Int?,
    @SerialName("type_id")
    var typeId: Int?,
    @SerialName("security_data")
    var securityData: String,
    @SerialName("security_value")
    var securityValue: String?,
)
