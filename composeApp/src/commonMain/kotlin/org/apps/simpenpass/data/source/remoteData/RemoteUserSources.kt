package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.UserResponseData
import org.apps.simpenpass.utils.Constants

class RemoteUserSources(private val httpClient: HttpClient) : UserDataFunc {

    override suspend fun login(data: LoginRequest) : BaseResponse<UserResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "login"){
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(data.email,data.password))
            }
            return response.body<BaseResponse<UserResponseData>>()

        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }

    override suspend fun register(data: RegisterRequest): BaseResponse<UserResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "register"){
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(data.name,data.email,data.password))
            }
            return response.body<BaseResponse<UserResponseData>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }

    override suspend fun logout(token: String?): BaseResponse<UserResponseData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "logout"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<UserResponseData>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }
}