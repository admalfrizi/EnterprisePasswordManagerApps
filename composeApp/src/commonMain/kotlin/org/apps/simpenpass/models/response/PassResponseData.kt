package org.apps.simpenpass.models.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassResponseData(
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
    @SerialName("is_encrypted")
    var isEncrypted: Boolean?,
    @SerialName("password")
    var password: String?,
    @SerialName("url")
    var url: String?,
    @SerialName("user_id")
    val userId: Int?,
    @SerialName("username")
    var username: String?
)