package org.apps.simpenpass.data.source.remoteData

import org.apps.simpenpass.models.UserData
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse

interface RemoteDataFunc {
    suspend fun login(data: LoginRequest): BaseResponse<UserData>
    suspend fun register(data: RegisterRequest) : BaseResponse<UserData>

}