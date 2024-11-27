package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.remoteData.RemoteResetPassSources
import org.apps.simpenpass.utils.NetworkResult

class ForgotPassRepository(
    private val remoteResetPassSources: RemoteResetPassSources
)  {
    fun sendOtp(email : String) = flow {
        emit(NetworkResult.Loading())
        val result = remoteResetPassSources.sendOtp(email)

        when(result.success){
            true -> emit(NetworkResult.Success(result))
            false -> emit(NetworkResult.Error(result.message))
        }

        Napier.v("otp data $result")
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
        Napier.v("otp error ${it.message}")
    }

    fun verifyOtp(otp: Int, isResetPass: Boolean, id : String) = flow {
        emit(NetworkResult.Loading())
        val result = remoteResetPassSources.verifyOtp(otp, isResetPass ,id.toInt())
        emit(NetworkResult.Success(result))

        when(result.success){
            true -> emit(NetworkResult.Success(result))
            false -> emit(NetworkResult.Error(result.message))
        }

        Napier.v("verify otp data $result")
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
        Napier.v("verify otp error ${it.message}")
    }

    fun resetPassword(token: String, password: String) = flow {
        emit(NetworkResult.Loading())
        val result = remoteResetPassSources.resetPassword(token,password)

        when(result.success){
            true -> emit(NetworkResult.Success(result))
            false -> emit(NetworkResult.Error(result.message))
        }
        Napier.v("result reset pass : $result")
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
        Napier.v("error reset pass : ${it.message}")
    }
}