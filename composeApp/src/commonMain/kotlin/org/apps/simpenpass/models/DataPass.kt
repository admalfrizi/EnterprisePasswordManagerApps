package org.apps.simpenpass.models

import kotlin.uuid.Uuid

data class DataPass(
    val id: Int,
    val accountName : String,
    val email: String
)
