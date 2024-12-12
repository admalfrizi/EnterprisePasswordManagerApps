package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataPass(
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
    @SerialName("is_encrypted")
    var isEncrypted: Boolean,
    @SerialName("username")
    var username: String?
)
