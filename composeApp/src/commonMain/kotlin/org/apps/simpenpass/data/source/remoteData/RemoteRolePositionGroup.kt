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
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.AddRoleRequest
import org.apps.simpenpass.models.request.UpdateRoleMemberGroupRequest
import org.apps.simpenpass.models.request.UpdateRoleNameRequest
import org.apps.simpenpass.models.response.AddRoleReponse
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.DetailRoleGroupResponse
import org.apps.simpenpass.models.response.UpdateRoleMemberResponse
import org.apps.simpenpass.utils.Constants

class RemoteRolePositionGroup(private val httpClient: HttpClient) : RolePositionFunc {
    override suspend fun addRolePositionInGroup(
        token: String,
        role: AddRoleRequest,
        groupId: Int
    ): BaseResponse<AddRoleReponse> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addRolePosition/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(role)
            }

            return response.body<BaseResponse<AddRoleReponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
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

    override suspend fun updateRoleMemberGroup(
        token: String,
        groupId: Int,
        updateRoleMember: UpdateRoleMemberGroupRequest
    ): BaseResponse<UpdateRoleMemberResponse> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "changeRoleMember/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(updateRoleMember)
            }

            return response.body<BaseResponse<UpdateRoleMemberResponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun detailRoleData(
        token: String,
        roleId: Int
    ): BaseResponse<DetailRoleGroupResponse> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "detailsRole/$roleId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<DetailRoleGroupResponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun deleteRolePosition(
        token: String,
        groupId: Int,
        roleId : Int
    ): BaseResponse<AddRoleReponse> {
        try {
            val response : HttpResponse = httpClient.delete(Constants.BASE_API_URL + "deleteRoleGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("roleId",roleId)
            }

            return response.body<BaseResponse<AddRoleReponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updateRoleNamePosition(
        token: String,
        roleId: Int,
        updateRoleName: UpdateRoleNameRequest
    ): BaseResponse<AddRoleReponse> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateRoleName/$roleId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(updateRoleName)
            }

            return response.body<BaseResponse<AddRoleReponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }


}