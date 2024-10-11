package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import org.apps.simpenpass.models.request.InsertDataRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData

class RemoteGroupDataSources(private val httpClient: HttpClient) : GroupPassDataFunc {
    override suspend fun createUserPass(
        token: String,
        formData: InsertDataRequest,
        id: Int
    ): BaseResponse<PassResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun editPassData(data: RegisterRequest): BaseResponse<PassResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun listUserPassData(
        token: String,
        id: Int
    ): BaseResponse<List<PassResponseData>> {
        TODO("Not yet implemented")
    }

}