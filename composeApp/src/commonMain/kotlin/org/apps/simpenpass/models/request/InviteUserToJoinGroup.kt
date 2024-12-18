package org.apps.simpenpass.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InviteUserToJoinGroup(
    @SerialName("group_id")
    val groupId: Int,
    @SerialName("list_email")
    val listEmailToInvite: List<SendEmailRequest>
)

@Serializable
data class SendEmailRequest(
    @SerialName("user_id")
    val userId : Int,
    @SerialName("email")
    val email : String,
)