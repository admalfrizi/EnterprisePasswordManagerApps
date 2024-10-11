package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertDataRequest(
    @SerialName("account_name")
    val accountName: String?,
    @SerialName("desc")
    val desc: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("jenis_data")
    val jenisData: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("username")
    val username: String?
)
