package org.apps.simpenpass.data.repository

import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.utils.NetworkResult

class AuthRepository(private val remoteUserSources: RemoteUserSources) {
    fun login(data: LoginRequest)  = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.login(data)
            emit(NetworkResult.Success(userData.data))
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e))
        }
    }
}