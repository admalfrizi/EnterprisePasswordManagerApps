package org.apps.simpenpass.models.request

data class AddMemberRequest(
    val add_member: List<AddMember>
)

data class AddMember(
    val user_id: Int,
    val isGroupAdmin: Boolean? = false
)
