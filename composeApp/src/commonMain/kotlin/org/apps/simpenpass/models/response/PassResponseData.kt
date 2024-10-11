package org.apps.simpenpass.models.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassResponseData(
    @SerialName("account_name")
    val accountName: String,
    @SerialName("desc")
    val desc: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("jenis_data")
    val jenisData: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("user_id")
    val userId: Int?,
    @SerialName("username")
    val username: String?
)