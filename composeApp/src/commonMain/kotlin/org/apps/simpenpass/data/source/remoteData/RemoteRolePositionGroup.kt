package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.utils.Constants

class RemoteRolePositionGroup(private val httpClient: HttpClient) : RolePositionFunc {
    override suspend fun addRolePositionInGroup(
        token: String,
        insertData: AddGroupRequest,
        imgName: String,
        imgFile: ByteArray?
    ): BaseResponse<RoleGroupData> {
        TODO("Not yet implemented")
    }

    override suspend fun listRolePositionInGroup(
        token: String,
        groupId: Int
    ): BaseResponse<List<RoleGroupData>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "roleGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<List<RoleGroupData>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }
}