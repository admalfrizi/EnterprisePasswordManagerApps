package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationPassDataGroup(
    @SerialName("id")
    val id : Int,
    @SerialName("group_id")
    val groupId : Int,
    @SerialName("origin_group")
    val originGroup: String,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("email")
    val email: String
)
