package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.apps.simpenpass.models.user_data.LocalUserStore
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.utils.NetworkResult

class UserRepository(private val remoteUserSources: RemoteUserSources,private val localData : LocalStoreData) {
    fun login(data: LoginRequest)  = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.login(data)
            if(userData.success){
                userData.data?.user?.let { localData.saveUserData(it) }
                userData.data?.accessToken?.let { localData.saveUserToken(it) }
                localData.setLoggedInStatus(true)
                emit(NetworkResult.Success(userData.data?.user))
            } else {
                emit(NetworkResult.Error(userData.message))
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }

    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Response Message: ${error.message}")
    }

    fun register(data: RegisterRequest) = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.register(data)
            if(userData.success){
                emit(NetworkResult.Success(userData.data?.user))
            } else {
                emit(NetworkResult.Error(userData.message))
            }
            Napier.d("Response Data: $userData")
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Response Message: ${error.message}")
    }

    fun logout(token: String?) = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.logout(token!!)
            if(userData.success){
                emit(userData.data?.let { NetworkResult.Success(it.user) })
            } else {
                emit(NetworkResult.Error(userData.message))
            }
            localData.setLoggedInStatus(false)
            localData.clearUserData()
            Napier.v("Response Message: $userData")
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Error Message: ${error.message}")
    }

    suspend fun getUserData(): LocalUserStore {
        return localData.getUserData()
    }

    fun getToken(): Flow<String> {
        return localData.getToken
    }

    suspend fun getStatusLoggedIn(): Flow<Boolean> {
        return localData.checkLoggedIn()
    }
}