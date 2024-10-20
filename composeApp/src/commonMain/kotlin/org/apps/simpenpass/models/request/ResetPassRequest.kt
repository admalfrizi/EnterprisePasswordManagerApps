package org.apps.simpenpass.models.request

data class ResetPassRequest(
    val email: String,
    val password: String,
)
