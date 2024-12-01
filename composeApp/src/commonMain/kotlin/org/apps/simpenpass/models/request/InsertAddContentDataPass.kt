package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertAddContentDataPass(
    @SerialName("id")
    val id: Int,
    @SerialName("nm_data")
    val nmData: String,
    @SerialName("vl_data")
    val vlData: String,
)
