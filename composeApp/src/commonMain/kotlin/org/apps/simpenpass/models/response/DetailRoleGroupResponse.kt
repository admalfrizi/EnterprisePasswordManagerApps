package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apps.simpenpass.models.user_data.UserData

@Serializable
data class DetailRoleGroupResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("nm_posisi")
    val nmPosisi: String,
    @SerialName("anggota_group")
    val anggotaGrup: List<UserData>? = emptyList()
)
