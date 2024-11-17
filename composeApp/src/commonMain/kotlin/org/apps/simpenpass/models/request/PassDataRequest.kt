package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassDataRequest(
    @SerialName("account_name")
    var accountName: String?,
    @SerialName("desc")
    var desc: String?,
    @SerialName("email")
    var email: String?,
    @SerialName("jenis_data")
    var jenisData: String?,
    @SerialName("password")
    var password: String?,
    @SerialName("url")
    var url: String?,
    @SerialName("username")
    var username: String?
)
