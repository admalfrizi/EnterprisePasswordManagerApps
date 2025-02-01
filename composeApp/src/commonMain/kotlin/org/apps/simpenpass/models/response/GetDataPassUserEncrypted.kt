package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDataPassUserEncrypted(
    @SerialName("list_pass_data")
    val listPassData: List<GetPassDataGroup>,
    @SerialName("total_data_count")
    val totalDataCount: Int
)
