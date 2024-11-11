package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoleGroupData(
    @SerialName("id")
    val id: Int,
    @SerialName("nm_posisi")
    val nmPosisi: String,
    @SerialName("jmlh_anggota")
    val jmlhAnggota: Int
)
