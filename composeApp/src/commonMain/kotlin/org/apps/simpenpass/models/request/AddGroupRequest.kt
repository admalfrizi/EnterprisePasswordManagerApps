package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddGroupRequest(
    @SerialName("nm_group")
    val nmGroup: String,
    @SerialName("desc")
    val desc: String? = null
)
