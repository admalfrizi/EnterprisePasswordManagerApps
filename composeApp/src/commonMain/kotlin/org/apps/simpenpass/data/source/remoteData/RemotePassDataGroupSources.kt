package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import org.apps.simpenpass.models.pass_data.PassDataGroup
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassDataGroupByIdResponse
import org.apps.simpenpass.models.response.PassResponseData
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

    override suspend fun addPassGroup(token: String,groupId: Int,roleId: Int,addDataPass: PassDataRequest) : BaseResponse<PassResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addPassDataToGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("roleId", roleId)
                setBody(addDataPass)
            }

            return response.body<BaseResponse<PassResponseData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updatePassGroup() {
        TODO("Not yet implemented")
    }
}