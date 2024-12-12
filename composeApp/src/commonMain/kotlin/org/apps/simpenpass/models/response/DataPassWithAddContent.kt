package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apps.simpenpass.models.pass_data.AddContentPassData

@Serializable
data class DataPassWithAddContent(
    @SerialName("id")
    val id: Int?,
    @SerialName("account_name")
    var accountName: String,
    @SerialName("email")
    var email: String?,
    @SerialName("jenis_data")
    var jenisData: String?,
    @SerialName("password")
    var password: String?,
    @SerialName("url")
    var url: String?,
    @SerialName("username")
    var username: String?,
    @SerialName("desc")
    var desc: String?,
    @SerialName("is_encrypted")
    var isEncrypted: Boolean?,
    @SerialName("add_pass_content")
    var addContentPass: List<AddContentPassData>?
)
