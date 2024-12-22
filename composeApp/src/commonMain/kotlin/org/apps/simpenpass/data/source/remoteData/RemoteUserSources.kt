package org.apps.simpenpass.data.source.remoteData

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
import org.apps.simpenpass.models.pass_data.RecommendationPassDataGroup
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.request.UpdateUserDataRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.UserResponseData
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.models.user_data.UserDataStats
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
                setBody(data)
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

    override suspend fun userDataStats(
        token: String,
        userId: Int
    ): BaseResponse<UserDataStats> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "userDataPassStats/$userId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            return response.body<BaseResponse<UserDataStats>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }

    override suspend fun updateUserData(
        token: String,
        userId: Int,
        updateUser: UpdateUserDataRequest
    ): BaseResponse<UserData> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateUserData/$userId"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(updateUser)
            }
            return response.body<BaseResponse<UserData>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }

    override suspend fun verifyPass(
        token: String,
        userId: Int,
        password: String
    ): BaseResponse<Boolean> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "verifyPass"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("userId", userId)
                setBody(MultiPartFormDataContent(
                    formData {
                        append("password",password)
                    }
                ))
            }
            return response.body<BaseResponse<Boolean>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }

    override suspend fun dataPassGroupRecommendation(token: String,userId: Int): BaseResponse<List<RecommendationPassDataGroup>> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "randomRecommendDataPassGroupJoined"){
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                parameter("userId", userId)
            }
            return response.body<BaseResponse<List<RecommendationPassDataGroup>>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        }
    }
}