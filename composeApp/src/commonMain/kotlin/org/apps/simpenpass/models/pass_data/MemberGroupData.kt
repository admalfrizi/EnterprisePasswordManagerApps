package org.apps.simpenpass.models.pass_data

data class MemberGroupData(
    val id: Int,
    val nm_people: String,
    val email: String,
    var isGroupAdmin: Boolean? = false,
    val role_position: String,
)