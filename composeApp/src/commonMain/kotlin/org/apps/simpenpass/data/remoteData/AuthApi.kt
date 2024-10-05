package org.apps.simpenpass.data.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.UserData
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.utils.Constants
import org.apps.simpenpass.utils.NetworkResult

class AuthApi(
    private val httpClient : HttpClient
) {
    suspend fun login(email: String, password: String): NetworkResult<UserData> {
        try {
            val response : HttpResponse = httpClient.post("login"){
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email ,password))
            }

           response.status.value.let { statusCode ->
                when(statusCode){
                    in 200..299 -> {
                        return NetworkResult.success(response.body<UserData>())
                    }
                    401 -> {
                        return NetworkResult.unauthorized("Maaf Anda Belum Login", null)
                    }
                    500 -> {
                        return NetworkResult.error("Server Anda Error",null)
                    }
                    504 -> {
                        return NetworkResult.timeout("Waktu Request Abis",null)
                    }
                    else -> {
                        return NetworkResult.unknown("Unknown Error", null)
                    }
                }
            }
        } catch (e: UnresolvedAddressException) {
           return NetworkResult.noInternet("No Internet Connection", null)
        }
    }
}