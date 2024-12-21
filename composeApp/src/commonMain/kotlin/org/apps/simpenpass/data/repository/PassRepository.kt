package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.request.FormAddContentPassData
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.request.SendUserDataPassToDecrypt
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
        listAddContentPassData: List<InsertAddContentDataPass>,
        deleteListAddContentPass: List<FormAddContentPassData>,
        updateListAddContentPass: List<FormAddContentPassData>,
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.editPassData(token, editData, passId)
                when(result.success){
                    true -> {

                        if(listAddContentPassData.isNotEmpty()){
                            remotePassSources.addContentDataPass(token,passId,listAddContentPassData)
                        }

                        if(updateListAddContentPass.isNotEmpty()){
                            remotePassSources.updateAddContentPassData(token,passId,updateListAddContentPass)
                        }

                        if(deleteListAddContentPass.isNotEmpty()){
                            remotePassSources.deleteAddContentPassData(token,passId,deleteListAddContentPass)
                        }

                        emit(NetworkResult.Success(result))

                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }

                    else -> {}
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

    fun deleteUserPassData(
        passId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.deleteUserPassData(token, passId)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }.onStart {
        emit(NetworkResult.Loading())
    }

    fun getUserDataPassEncrypted() = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.getUserDataPassEncrypted(token, localData.getUserData().id!!)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }.onStart {
        emit(NetworkResult.Loading())
    }

    fun updateUserDataPassWithNewKey(
        sendUserDataPassToDecrypt: SendUserDataPassToDecrypt,
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassSources.updateUserDataPassWithNewKey(token, sendUserDataPassToDecrypt)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
    }.onStart {
        emit(NetworkResult.Loading())
    }
}
