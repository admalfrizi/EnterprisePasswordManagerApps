package org.apps.simpenpass.data.source.remoteData

import io.github.aakira.napier.Napier
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
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.pass_data.AddContentPassData
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.LatestPassDataResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.Constants

class RemotePassDataSources(private val httpClient: HttpClient) : PassDataFunc {
    override suspend fun createUserPass(token: String, formData: PassDataRequest, id: Int): BaseResponse<PassResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "userPass"){
                contentType(ContentType.Application.Json)
                setBody(formData)
                parameter("userId", id)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<PassResponseData>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun editPassData(token: String, editData: PassDataRequest, passId: Int): BaseResponse<PassResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateData"){
                contentType(ContentType.Application.Json)
                setBody(editData)
                parameter("id", passId)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            return response.body<BaseResponse<PassResponseData>>()
        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun latestUserPassData(
        token: String,
        id: Int
    ): BaseResponse<LatestPassDataResponse> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "latestDataPass"){
                contentType(ContentType.Application.Json)
                parameter("userId", id)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            Napier.v("Response Code : ${response.status.value}")
            return response.body<BaseResponse<LatestPassDataResponse>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun listUserPassData(token: String, id: Int): BaseResponse<List<PassResponseData>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "userDataPass"){
                contentType(ContentType.Application.Json)
                parameter("userId", id)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<List<PassResponseData>>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun getUserPassDataById(
        token: String,
        passId: Int
    ): BaseResponse<PassResponseData> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "userPass/$passId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<PassResponseData>>()

        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun addContentDataPass(
        token: String,
        passId: Int,
        addContentPass: List<InsertAddContentDataPass>
    ): BaseResponse<List<AddContentPassData>> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "addContentPassData/$passId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(addContentPass)
            }

            Napier.v("Response Code : ${response.status.value}")
            return response.body<BaseResponse<List<AddContentPassData>>>()

        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun listContentData(token: String, passId: Int): BaseResponse<List<AddContentPassData>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "listAddDataPass/$passId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<List<AddContentPassData>>>()

        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

}