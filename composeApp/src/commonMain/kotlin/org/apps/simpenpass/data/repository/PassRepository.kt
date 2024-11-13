package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.utils.NetworkResult

class PassRepository(
    private val remotePassSources: RemotePassDataSources,
    private val localData : LocalStoreData
) {
     fun createUserPassData(
         formData: PassDataRequest,
         insertAddContentDataPass: List<InsertAddContentDataPass>
     ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val userId = localData.getUserData().id
                val createUserPass = remotePassSources.createUserPass(token,formData,userId!!)

                if(createUserPass.success && createUserPass.data?.id != null){
                    remotePassSources.addContentDataPass(token,createUserPass.data.id, insertAddContentDataPass)
                    emit(NetworkResult.Success(createUserPass))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
         Napier.d("Error Data ${error.message}")
    }

    fun editUserPassData(
        editData: PassDataRequest,
        passId: Int,
        listAddContentPassData: List<InsertAddContentDataPass>
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.editPassData(token, editData, passId)
                val updateAddContentData = remotePassSources.addContentDataPass(token,passId,listAddContentPassData)

                if(result.success && updateAddContentData.success){
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

    fun latestListDataPass() = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val userId = localData.getUserData().id
                val result = remotePassSources.latestUserPassData(token, userId!!)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
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
        Napier.v("Error Data ${error.message}")
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

    fun listContentData(
        passId: Int,
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.listContentData(token, passId)
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
