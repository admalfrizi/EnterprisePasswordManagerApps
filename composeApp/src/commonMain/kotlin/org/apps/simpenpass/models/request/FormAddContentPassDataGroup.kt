package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormAddContentPassDataGroup(
    @SerialName("id")
    var id: Int,
    @SerialName("nm_data")
    var nmData: String,
    @SerialName("vl_data")
    var vlData: String
)
