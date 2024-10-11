package org.apps.simpenpass.models.request

data class InsertDataRequest(
    val userId: Int?,
    val accountName: String?,
    val desc: String?,
    val email: String?,
    val jenisData: String?,
    val password: String?,
    val url: String?,
    val username: String?
)
