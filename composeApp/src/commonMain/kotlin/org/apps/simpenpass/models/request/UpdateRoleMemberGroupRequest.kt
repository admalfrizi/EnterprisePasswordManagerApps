package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRoleMemberGroupRequest(
    @SerialName("anggota_id")
    val anggotaId: Int,
    @SerialName("posisi_id")
    val posisiId: Int
)
