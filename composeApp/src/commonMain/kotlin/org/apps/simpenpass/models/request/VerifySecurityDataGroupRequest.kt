package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifySecurityDataGroupRequest(
    @SerialName("security_data")
    val securityData: String,
    @SerialName("security_value")
    val securityValue: String
)
