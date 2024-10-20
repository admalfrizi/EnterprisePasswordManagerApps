package org.apps.simpenpass.data.repository

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
        emit(NetworkResult.Success(result))
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }
}