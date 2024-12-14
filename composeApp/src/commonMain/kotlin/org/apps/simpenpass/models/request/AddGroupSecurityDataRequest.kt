package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddGroupSecurityDataRequest(
    @SerialName("type_id")
    val typeId: Int?,
    @SerialName("security_data")
    var securityData: String,
    @SerialName("security_value")
    var securityValue: String?,
)
