package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.utils.NetworkResult

class AuthRepository(private val remoteUserSources: RemoteUserSources) {
    fun login(data: LoginRequest)  = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.login(data)
            if(userData.success){
                emit(NetworkResult.Success(userData.data))
            } else {
                emit(NetworkResult.Error(userData.message))
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }

    fun register(data: RegisterRequest) = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.register(data)
            if(userData.success){
                emit(NetworkResult.Success(userData.data))
            } else {
                emit(NetworkResult.Error(userData.message))
            }
            Napier.d("Response: $userData")
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }
}