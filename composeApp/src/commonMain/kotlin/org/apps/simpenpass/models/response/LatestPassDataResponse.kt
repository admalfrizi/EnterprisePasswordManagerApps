package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apps.simpenpass.models.pass_data.DataPass

@Serializable
data class LatestPassDataResponse(
    @SerialName("total_data_origin_size")
    val totalDataOriginSize : Int,
    @SerialName("latest")
    val latest : List<DataPass>
)

