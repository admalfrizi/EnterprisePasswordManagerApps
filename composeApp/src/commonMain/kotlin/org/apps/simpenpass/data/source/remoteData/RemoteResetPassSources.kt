package org.apps.simpenpass.data.source.remoteData

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import org.apps.simpenpass.models.request.ResetPassRequest
import org.apps.simpenpass.models.request.SendOtpRequest
import org.apps.simpenpass.models.request.SendUserDataPassToDecrypt
import org.apps.simpenpass.models.request.VerifyOtpRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.GetDataPassUserEncrypted
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.models.response.VerifyOtpResponse
import org.apps.simpenpass.utils.Constants

class RemoteResetPassSources(
    private val httpClient: HttpClient
) : ResetPassFunc {
    override suspend fun sendOtp(email: String): BaseResponse<SendOtpResponse> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "sendOtp"){
                contentType(ContentType.Application.Json)
                setBody(SendOtpRequest(email))
            }
            return response.body<BaseResponse<SendOtpResponse>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun verifyOtp(otp: Int, isResetPass: Boolean,userId: Int): BaseResponse<VerifyOtpResponse> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "verifyOtp/$userId"){
                contentType(ContentType.Application.Json)
                setBody(VerifyOtpRequest(otp,isResetPass))
            }
            return response.body<BaseResponse<VerifyOtpResponse>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun resetPassword(password: String, token: String): BaseResponse<String> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "resetPass"){
                contentType(ContentType.Application.Json)
                setBody(ResetPassRequest(token,password))
            }
            return response.body<BaseResponse<String>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun getUserDataPassEncrypted(
        userId: Int
    ): BaseResponse<GetDataPassUserEncrypted> {
        try {
            val response : HttpResponse = httpClient.get(Constants.BASE_API_URL + "getPassDataEncrypted/$userId"){
                contentType(ContentType.Application.Json)
            }
            return response.body<BaseResponse<GetDataPassUserEncrypted>>()

        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun updateUserDataPassWithDecrypt(
        sendUserPassDataToChangeKey: SendUserDataPassToDecrypt
    ): BaseResponse<String> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "updateDataPass"){
                contentType(ContentType.Application.Json)
                setBody(sendUserPassDataToChangeKey)
            }
            return response.body<BaseResponse<String>>()

        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }

    override suspend fun verifyOldPassword(
        userId: Int,
        oldPass: String
    ): BaseResponse<Boolean> {
        try {
            val response : HttpResponse = httpClient.post(Constants.BASE_API_URL + "verifyOldPass"){
                contentType(ContentType.Application.Json)
                parameter("userId",userId)
                setBody(MultiPartFormDataContent(
                    formData {
                        append("oldPassword",oldPass)
                    }
                ))
            }
            return response.body<BaseResponse<Boolean>>()

        } catch (e: Exception){
            throw Exception(e.message)
        } catch (e: UnresolvedAddressException){
            throw Exception(e.message)
        }
    }
}