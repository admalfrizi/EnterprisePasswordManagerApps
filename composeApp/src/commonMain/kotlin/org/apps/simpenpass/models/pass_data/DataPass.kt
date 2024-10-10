package org.apps.simpenpass.models.pass_data

data class DataPass(
    val id: Int,
    val accountName : String,
    val email: String,
    val password: String,
    val url: String,
    val desc: String
)
