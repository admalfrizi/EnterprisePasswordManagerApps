package org.apps.simpenpass.models.pass_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassDataGroup(
    @SerialName("id")
    val id: Int,
    @SerialName("klmpk_role")
    val klmpkRole: String,
    @SerialName("nama_grup")
    val namaGrup: String,
    @SerialName("account_name")
    val accountName: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("url")
    val url: String,
    @SerialName("desc")
    val desc: String
)
