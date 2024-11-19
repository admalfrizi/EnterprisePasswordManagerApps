package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GrupPassData(
    @SerialName("id")
    val id: Int,
    @SerialName("img_grup")
    val img_grup: String? = null,
    @SerialName("nm_grup")
    val nm_grup: String,
    @SerialName("deskripsi_grup")
    val desc: String? = null
)

@Serializable
data class DtlGrupPass(
    @SerialName("group_dtl")
    val groupDtl: GrupDtl,
    @SerialName("total_pass_data")
    val totalPassData: Int,
    @SerialName("total_member")
    var totalMember: Int,
)

@Serializable
data class GrupDtl(
    @SerialName("id")
    val id: Int,
    @SerialName("img_grup")
    val img_grup: String? = null,
    @SerialName("nm_grup")
    var nm_grup: String,
    @SerialName("desc")
    var desc: String? = null
)