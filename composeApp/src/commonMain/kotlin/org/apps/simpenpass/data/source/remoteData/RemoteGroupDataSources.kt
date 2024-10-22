package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.utils.Constants

class RemoteGroupDataSources(private val httpClient: HttpClient) : GroupPassDataFunc {
    override suspend fun createGroup(
        token: String,
        insertData: AddGroupRequest,
        imgName: String?,
        imgFile: ByteArray?
    ): BaseResponse<GrupPassData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addGroup") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                if(imgFile?.isNotEmpty() == true){
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append("img_group", imgFile, Headers.build {
                                    append(HttpHeaders.ContentDisposition, "filename=\"${imgName}\"")
                                })
                            }
                        )
                    )
                }
                setBody(insertData)
            }

            return response.body<BaseResponse<GrupPassData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updateGroupData(data: RegisterRequest): BaseResponse<GrupPassData> {
        TODO("Not yet implemented")
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

    override suspend fun detailGroupData(token: String, groupId: Int): BaseResponse<DtlGrupPass> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "dtlGroup/$groupId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<DtlGrupPass>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

}