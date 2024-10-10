package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import org.apps.simpenpass.models.request.PassRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.models.response.UserResponseData

class RemotePassDataSources(private val httpClient: HttpClient) : PassDataFunc {
    override suspend fun createUserPass(data: PassRequest): BaseResponse<PassResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun editPassData(data: RegisterRequest): BaseResponse<UserResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(token: String?): BaseResponse<UserResponseData> {
        TODO("Not yet implemented")
    }
}