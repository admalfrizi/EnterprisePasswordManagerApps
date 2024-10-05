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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.models.UserData
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.utils.Constants
import org.apps.simpenpass.utils.NetworkResult

class AuthApi {
    private val httpClient = HttpClient()

    fun login(email: String, password: String): Flow<NetworkResult<UserData>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response : HttpResponse = httpClient.post("login"){
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email ,password))
            }
            emit(NetworkResult.Success(response.body<UserData>()))
        } catch (e: UnresolvedAddressException) {
           emit(NetworkResult.Error(e))
        }
    }.catch {
        emit(NetworkResult.Error(it))
    }
}