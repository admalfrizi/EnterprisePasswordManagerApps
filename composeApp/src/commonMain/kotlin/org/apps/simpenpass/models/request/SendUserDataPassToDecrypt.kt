package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendUserDataPassToDecrypt(
    @SerialName("dataPass")
    var dataPass : List<UpdateUserPassDataToDecrypt>
)

@Serializable
data class UpdateUserPassDataToDecrypt(
    @SerialName("pass_id")
    val passId : Int,
    @SerialName("password")
    val password: String,
    @SerialName("is_encrypted")
    val isEncrypted: Boolean
)