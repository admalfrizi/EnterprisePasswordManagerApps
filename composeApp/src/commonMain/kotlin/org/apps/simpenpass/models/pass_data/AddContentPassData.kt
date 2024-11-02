package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddContentPassData(
    @SerialName("id")
    val id : Int,
    @SerialName("pass_id")
    val passId : Int,
    @SerialName("nm_data")
    val nmData: String,
    @SerialName("vl_data")
    val vlData: String,
)
