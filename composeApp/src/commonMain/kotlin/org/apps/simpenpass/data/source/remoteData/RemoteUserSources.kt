package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.UserData
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.response.BaseResponse

class RemoteUserSources(private val httpClient: HttpClient) : RemoteDataFunc {

    override suspend fun login(data: LoginRequest) : BaseResponse<UserData> {
        try {
            val response : HttpResponse = httpClient.post("login"){
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(data.email,data.password))
            }
            return response.body()

        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }
}