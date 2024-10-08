package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localStorage.DataPrefFunc
import org.apps.simpenpass.data.source.localStorage.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.apps.simpenpass.models.LocalUserStore
import org.apps.simpenpass.models.UserData
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.utils.NetworkResult

class AuthRepository(private val remoteUserSources: RemoteUserSources,private val localData : LocalStoreData) {
    fun login(data: LoginRequest)  = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.login(data)
            if(userData.success){
                userData.data?.user?.let { localData.saveUserData(it) }
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
            Napier.d("Response: $userData")
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Response Message: ${error.message}")
    }

    fun logout() = flow {
        emit(NetworkResult.Loading())
        try {
            val storeToken = localData.getToken()
            if (storeToken != null) {
                if(storeToken.isNotEmpty()){
                    val userData = remoteUserSources.logout(storeToken)
                    if(userData.success){
                        emit(NetworkResult.Success(userData.data?.accessToken))
                        localData.clearToken()
                    } else {
                        emit(NetworkResult.Error(userData.message))
                    }
                    Napier.d("Response: $userData")
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Response Message: ${error.message}")
    }

    suspend fun getUserData(): LocalUserStore? {
        return localData.getUserData()
    }


}