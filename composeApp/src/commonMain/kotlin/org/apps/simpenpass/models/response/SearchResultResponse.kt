package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apps.simpenpass.models.user_data.UserData

@Serializable
data class SearchResultResponse(
    @SerialName("total_result")
    var totalResult: String,
    @SerialName("data")
    var data: List<UserData>?,
)

