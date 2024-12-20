package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendDataPassToDecrypt(
    @SerialName("dataPass")
    var dataPass : List<UpdatePassDataToDecrypt>
)
