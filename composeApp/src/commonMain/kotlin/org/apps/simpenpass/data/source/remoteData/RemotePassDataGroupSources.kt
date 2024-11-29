package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.pass_data.AddContentPassDataGroup
import org.apps.simpenpass.models.pass_data.PassDataGroup
import org.apps.simpenpass.models.request.DeleteAddContentPassDataGroup
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataGroupRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassDataGroupByIdResponse
import org.apps.simpenpass.models.response.PassGroupResponseData
import org.apps.simpenpass.utils.Constants

class RemotePassDataGroupSources(private val httpClient: HttpClient) : PassDataGroupFunc {
    override suspend fun listGroupPassword(token: String,groupId: Int): BaseResponse<List<PassDataGroup>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "allPassGroupData/$groupId")
            {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<List<PassDataGroup>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun listGroupPasswordRoleFiltered(
        token: String,
        groupId: Int,
        roleId: Int
    ): BaseResponse<List<PassDataGroup>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "filteredRoleBasedPassDataGroup/$groupId")
            {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("roleId", roleId)
            }
            return response.body<BaseResponse<List<PassDataGroup>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun getDataPassGroupById(
        token: String,
        groupId: Int,
        passDataGroupId: Int
    ): BaseResponse<PassDataGroupByIdResponse> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "loadDataPassGroupById/$groupId")
            {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("passDataGroupId", passDataGroupId)
            }
            return response.body<BaseResponse<PassDataGroupByIdResponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun addPassGroup(token: String,groupId: Int,addDataPass: PassDataGroupRequest) : BaseResponse<PassGroupResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addPassDataToGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(addDataPass)
            }

            return response.body<BaseResponse<PassGroupResponseData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updatePassGroup(
        token: String,
        groupId: Int,
        passDataGroupId: Int,
        updatePassData: PassDataGroupRequest
    ): BaseResponse<PassGroupResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updatePassDataGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("passGroupId", passDataGroupId)
                setBody(updatePassData)
            }

            return response.body<BaseResponse<PassGroupResponseData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun addContentPassData(
        token: String,
        groupId: Int,
        passGroupDataId: Int,
        addContentPassData: List<InsertAddContentDataPass>
    ): BaseResponse<List<AddContentPassDataGroup>> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addContentPassDataGroup/$groupId/$passGroupDataId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(addContentPassData)
            }

            return response.body<BaseResponse<List<AddContentPassDataGroup>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun deleteAddContentPassData(
        token: String,
        passGroupDataId: Int,
        deleteAddContentPassData: List<DeleteAddContentPassDataGroup>
    ): BaseResponse<List<AddContentPassDataGroup>> {
        try {
            val response : HttpResponse = httpClient.delete(Constants.BASE_API_URL + "deleteAddDataPassContentGroup/$passGroupDataId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(deleteAddContentPassData)
            }

            return response.body<BaseResponse<List<AddContentPassDataGroup>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }
}