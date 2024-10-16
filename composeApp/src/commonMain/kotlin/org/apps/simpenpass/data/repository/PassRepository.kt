package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.utils.NetworkResult

class PassRepository(
    private val remotePassSources: RemotePassDataSources,
    private val localData : LocalStoreData
) {
     fun createUserPassData(formData: PassDataRequest) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val userId = localData.getUserData().id
                val result = remotePassSources.createUserPass(token,formData,userId!!)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
         Napier.d("Error Data ${error.message}")
    }

    fun editUserPassData(editData: PassDataRequest, passId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.editPassData(token, editData, passId)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Error Data ${error.message}")
    }

    fun listUserPassData() = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val userId = localData.getUserData().id
                val result = remotePassSources.listUserPassData(token, userId!!)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }

    fun testListUserPassData(token: String, userId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            val result = remotePassSources.listUserPassData(token, userId)
            if(result.success){
                emit(NetworkResult.Success(result))
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }

    fun getUserPassDataById(passId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.getUserPassDataById(token, passId)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }
}
