package org.apps.simpenpass.data.source.remoteData

import org.apps.simpenpass.models.request.InsertDataRequest
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.models.response.UserResponseData

interface UserDataFunc {
    suspend fun login(data: LoginRequest): BaseResponse<UserResponseData>
    suspend fun register(data: RegisterRequest) : BaseResponse<UserResponseData>
    suspend fun logout(token: String?) : BaseResponse<UserResponseData>
}

interface PassDataFunc {
    suspend fun createUserPass(token: String,formData: InsertDataRequest, id: Int): BaseResponse<PassResponseData>
    suspend fun editPassData(data: RegisterRequest) : BaseResponse<PassResponseData>
    suspend fun listUserPassData(token: String, id: Int) : BaseResponse<List<PassResponseData>>
}

interface GroupPassDataFunc {
    suspend fun createUserPass(token: String,formData: InsertDataRequest, id: Int): BaseResponse<PassResponseData>
    suspend fun editPassData(data: RegisterRequest) : BaseResponse<PassResponseData>
    suspend fun listUserPassData(token: String, id: Int) : BaseResponse<List<PassResponseData>>
}