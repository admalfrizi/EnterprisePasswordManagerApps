package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberGroupData(
    @SerialName("id")
    val id: Int,
    @SerialName("posisi_id")
    val posisiId: Int?,
    @SerialName("nama_anggota")
    val nama_anggota: String,
    @SerialName("email_anggota")
    val email_anggota: String,
    @SerialName("nama_posisi")
    val nmPosisi: String?,
    @SerialName("isGroupAdmin")
    var isGroupAdmin: Boolean? = false
)