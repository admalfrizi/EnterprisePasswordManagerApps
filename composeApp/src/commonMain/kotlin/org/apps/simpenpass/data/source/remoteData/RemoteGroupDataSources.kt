package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.request.InsertDataRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData

class RemoteGroupDataSources(private val httpClient: HttpClient) : GroupPassDataFunc {
    override suspend fun createGroup(
        token: String,
        formData: InsertDataRequest,
        id: Int
    ): BaseResponse<GrupPassData> {
        TODO("Not yet implemented")
    }

    override suspend fun updateGroupData(data: RegisterRequest): BaseResponse<GrupPassData> {
        TODO("Not yet implemented")
    }

    override suspend fun listUserJoinedGroup(
        token: String,
        userId: Int
    ): BaseResponse<List<PassResponseData>> {
        TODO("Not yet implemented")
    }

}