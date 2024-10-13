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
import org.apps.simpenpass.models.request.InsertDataRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.Constants

class RemotePassDataSources(private val httpClient: HttpClient) : PassDataFunc {
    override suspend fun createUserPass(token: String, formData: InsertDataRequest,id: Int): BaseResponse<PassResponseData> {
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

    override suspend fun editPassData(data: RegisterRequest): BaseResponse<PassResponseData> {
        TODO("Not yet implemented")
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

}