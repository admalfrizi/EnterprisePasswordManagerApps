package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupSecurityTypeResponse(
    @SerialName("id")
    val id : Int,
    @SerialName("nm_option")
    val nmOption: String
)