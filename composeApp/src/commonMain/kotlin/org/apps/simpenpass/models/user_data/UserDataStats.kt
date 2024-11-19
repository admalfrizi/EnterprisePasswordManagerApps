package org.apps.simpenpass.models.user_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataStats(
    @SerialName("total_pass_data")
    val totalPassData: Int,
    @SerialName("total_joined_group")
    val totalJoinedGroup: Int,
)
