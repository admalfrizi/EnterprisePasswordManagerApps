package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPassDataGroup(
    @SerialName("id")
    val id: Int,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("password")
    val password: String,
    @SerialName("is_encrypted")
    val isEncrypted: Boolean
)
