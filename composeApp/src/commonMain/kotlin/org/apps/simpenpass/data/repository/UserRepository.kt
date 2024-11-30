package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteResetPassSources
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.apps.simpenpass.models.user_data.LocalUserStore
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.request.UpdateUserDataRequest
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.utils.NetworkResult

class UserRepository(
    private val remoteUserSources: RemoteUserSources,
    private val remoteResetPassSources: RemoteResetPassSources,
    private val localData : LocalStoreData
){
    fun login(data: LoginRequest) = flow {
        emit(NetworkResult.Loading())
        try {
            val userData = remoteUserSources.login(data)
            if (userData.success) {
                userData.data?.user?.let { localData.saveUserData(it) }
                userData.data?.accessToken?.let { localData.saveUserToken(it) }
                localData.setLoggedInStatus(true)
                emit(NetworkResult.Success(userData.data?.user))
            } else {
                emit(NetworkResult.Error(userData.message))
            }
        } catch (e: UnresolvedAddressException) {
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
            if (userData.success) {
                emit(NetworkResult.Success(userData.data?.user))
            } else {
                emit(NetworkResult.Error(userData.message))
            }
            Napier.d("Response Data: $userData")
        } catch (e: UnresolvedAddressException) {
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
            if (userData.success) {
                emit(userData.data?.let { NetworkResult.Success(it.user) })
            } else {
                emit(NetworkResult.Error(userData.message))
            }
            localData.setLoggedInStatus(false)
            localData.clearUserData()
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }

    suspend fun getUserData(): LocalUserStore {
        return localData.getUserData()
    }

    suspend fun saveUserData(user: UserData) {
        return localData.saveUserData(user)
    }

    fun getToken(): Flow<String> {
        return localData.getToken
    }

    suspend fun getStatusLoggedIn(): Flow<Boolean> {
        return localData.checkLoggedIn()
    }

    fun getUserDataStats(
        userId: Int
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteUserSources.userDataStats(token, userId)
                when (result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }

                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun changeDataOtp(
        email: String
    ) = flow {
        try {
            val result = remoteResetPassSources.sendOtp(email)
            when (result.success) {
                true -> {
                    emit(NetworkResult.Success(result))
                }

                false -> {
                    emit(NetworkResult.Error(result.message))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun updateDataUser(
        userId: Int,
        updateUser: UpdateUserDataRequest
    ) = flow {
        localData.getToken.collect { token ->
            val result = remoteUserSources.updateUserData(token, userId,updateUser)
            when (result.success) {
                true -> {
                    emit(NetworkResult.Success(result))
                }
                false -> {
                    emit(NetworkResult.Error(result.message))
                }
            }
        }
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }
}