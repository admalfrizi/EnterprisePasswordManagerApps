package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePassDataGroupToDecrypt(
    @SerialName("pass_group_id")
    val passGroupId : Int,
    @SerialName("password")
    val password: String,
    @SerialName("is_encrypted")
    val isEncrypted: Boolean
)
