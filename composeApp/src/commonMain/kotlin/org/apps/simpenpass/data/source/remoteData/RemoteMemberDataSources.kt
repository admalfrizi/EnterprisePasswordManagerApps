package org.apps.simpenpass.data.source.remoteData

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
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
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.request.UpdateAdminMemberGroupRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.SearchResultResponse
import org.apps.simpenpass.models.response.UpdateAdminMemberResponse
import org.apps.simpenpass.models.response.UserJoinResponse
import org.apps.simpenpass.utils.Constants

class RemoteMemberDataSources(private val httpClient: HttpClient) : MemberGroupDataFunc {
    override suspend fun addMemberToGroup(
        token: String,
        addData: List<AddMemberRequest>,
        groupId: Int
    ): BaseResponse<List<AddMemberRequest>> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addMemberGroup/$groupId"){
                contentType(ContentType.Application.Json)
                setBody(addData)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            Napier.v("Data Add Group ID : $groupId")
            return response.body<BaseResponse<List<AddMemberRequest>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun deleteOneMemberFromGroup(
        token: String,
        userId: Int
    ): BaseResponse<GrupPassData> {
        TODO("Not yet implemented")
    }

    override suspend fun listUserJoinedInGroup(
        token: String,
        groupId: Int
    ): BaseResponse<List<MemberGroupData>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "memberGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<List<MemberGroupData>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun findUsersToJoinedGroup(
        token: String,
        query: String
    ): BaseResponse<SearchResultResponse> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "searchUser"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("query", query)
            }

            return response.body<BaseResponse<SearchResultResponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updateAdminMemberGroup(
        token: String,
        groupId: Int ,listUpdate: List<UpdateAdminMemberGroupRequest>
    ): BaseResponse<List<UpdateAdminMemberResponse>> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateAdminMember/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(listUpdate)
            }

            return response.body<BaseResponse<List<UpdateAdminMemberResponse>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun userJoinToGroup(
        token: String,
        groupId: Int,
        userId: Int
    ): BaseResponse<UserJoinResponse> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "userJoinToGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(MultiPartFormDataContent(
                    formData {
                        append("user_id", userId)
                    }
                ))
            }

            return response.body<BaseResponse<UserJoinResponse>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }


}