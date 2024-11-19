package org.apps.simpenpass.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apps.simpenpass.models.pass_data.AddContentPassDataGroup

@Serializable
data class PassDataGroupByIdResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("posisi_id")
    var posisiId: Int?,
    @SerialName("group_id")
    val groupId: String,
    @SerialName("jenis_data")
    var jenisData: String?,
    @SerialName("account_name")
    var accountName: String,
    @SerialName("username")
    var username: String?,
    @SerialName("email")
    var email: String?,
    @SerialName("password")
    var password: String?,
    @SerialName("url")
    var url: String?,
    @SerialName("desc")
    var desc: String?,
    @SerialName("add_pass_content")
    var addPassContent: List<AddContentPassDataGroup>
)
