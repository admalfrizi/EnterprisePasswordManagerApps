package org.apps.simpenpass.models.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassGroupResponseData(
    @SerialName("account_name")
    var accountName: String,
    @SerialName("desc")
    var desc: String?,
    @SerialName("email")
    var email: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("jenis_data")
    var jenisData: String?,
    @SerialName("password")
    var password: String?,
    @SerialName("url")
    var url: String?,
    @SerialName("group_id")
    val groupId: Int?,
    @SerialName("username")
    var username: String?,
    @SerialName("is_encrypted")
    var isEncrypted: Boolean,
)