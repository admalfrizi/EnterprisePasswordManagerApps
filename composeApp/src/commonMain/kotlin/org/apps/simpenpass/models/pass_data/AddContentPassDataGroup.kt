package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddContentPassDataGroup(
    @SerialName("id")
    val id : Int,
    @SerialName("pass_group_id")
    val passGroupId : Int,
    @SerialName("nm_data")
    val nmData: String,
    @SerialName("vl_data")
    val vlData: String,
)
