package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GroupSecurityData
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.AddGroupSecurityDataRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.GroupSecurityTypeResponse
import org.apps.simpenpass.utils.Constants

class RemoteGroupDataSources(private val httpClient: HttpClient) : GroupPassDataFunc {
    override suspend fun createGroup(
        token: String,
        insertData: AddGroupRequest,
        imgName: String,
        imgFile: ByteArray?
    ): BaseResponse<GrupPassData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addGroup")
            {
                contentType(ContentType.MultiPart.FormData)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(MultiPartFormDataContent(
                    formData {
                        if(imgFile != null){
                            append("img_group",imgFile, Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"img_group\"; filename=\"$imgName\"")
                            })
                        }
                        append("nm_group" ,insertData.nmGroup)
                        append("desc", insertData.desc ?: "")
                    }
                ))
            }
            return response.body<BaseResponse<GrupPassData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }


    override suspend fun updateGroupData(
        token: String,
        groupId: Int,
        data: AddGroupRequest,
        imgName: String,
        imgFile: ByteArray?
    ): BaseResponse<GrupPassData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateGroup/$groupId")
            {
                contentType(ContentType.MultiPart.FormData)
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(MultiPartFormDataContent(
                    formData {
                        if(imgFile != null){
                            append("img_group",imgFile, Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"img_group\"; filename=\"$imgName\"")
                            })
                        }
                        append("nm_group" ,data.nmGroup)
                        append("desc", data.desc ?: "")
                    }
                ))
            }
            return response.body<BaseResponse<GrupPassData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun listJoinedGroupBasedOnUser(
        token: String,
        userId: Int
    ): BaseResponse<List<GrupPassData>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "groupPass/$userId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<List<GrupPassData>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun detailGroupData(token: String, groupId: Int,userId: Int): BaseResponse<DtlGrupPass> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "dtlGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("userId", userId)
            }

            return response.body<BaseResponse<DtlGrupPass>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun searchGroup(
        token: String,
        query: String
    ): BaseResponse<GrupPassData> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "searchGroup"){
                contentType(ContentType.Application.Json)
                parameter("query", query)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<GrupPassData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun getTypeSecurityGroup(token: String): BaseResponse<List<GroupSecurityTypeResponse>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "groupSecurityType"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<List<GroupSecurityTypeResponse>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun getGroupSecurityData(
        token: String,
        groupId: Int
    ): BaseResponse<List<GroupSecurityData>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "securityOptionData/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<List<GroupSecurityData>>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun addGroupSecurityData(
        token: String,
        addGroupSecurityData: AddGroupSecurityDataRequest,
        groupId: Int
    ): BaseResponse<GroupSecurityData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addSecurityOptionData/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(addGroupSecurityData)
            }

            return response.body<BaseResponse<GroupSecurityData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updateGroupSecurityData(
        token: String,
        addGroupSecurityData: AddGroupSecurityDataRequest,
        id: Int,
        groupId: Int
    ): BaseResponse<GroupSecurityData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateSecurityOptionData/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("id", id)
                setBody(addGroupSecurityData)
            }

            return response.body<BaseResponse<GroupSecurityData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun deleteGroupSecurityData(
        token: String,
        id: Int,
        groupId: Int
    ): BaseResponse<GroupSecurityData> {
        try {
            val response : HttpResponse = httpClient.delete(Constants.BASE_API_URL + "delSecurityOptionData/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("id", id)
            }

            return response.body<BaseResponse<GroupSecurityData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }
}