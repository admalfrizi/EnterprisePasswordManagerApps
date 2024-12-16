package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassDataGroupRequest(
    @SerialName("posisi_id")
    val posisiId: Int?,
    @SerialName("jenis_data")
    val jenisData: String?,
    @SerialName("username")
    val username: String?,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("email")
    val email: String?,
    @SerialName("password")
    val password: String,
    @SerialName("url")
    val url: String?,
    @SerialName("desc")
    val desc: String?,
    @SerialName("is_encrypted")
    var isEncrypted: Boolean?,
    @SerialName("add_pass_content")
    val addPassContent: List<InsertAddContentDataPass>?
)
