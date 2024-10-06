package org.apps.simpenpass.models.response

data class BaseResponse<T>(
    val success: Boolean,
    val code : Int,
    val message: String,
    val data: T? = null,
)
