package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteMemberDataResponse(
    @SerialName("nama_grup")
    val nama_grup: String,
    @SerialName("member")
    val member: MemberData
)

@Serializable
data class MemberData(
    @SerialName("member_id")
    val member_id: Int,
    @SerialName("nama_group")
    val nama_group: String,
    @SerialName("nama_anggota")
    val nama_anggota: String,
    @SerialName("email_anggota")
    val email_anggota: String
)


