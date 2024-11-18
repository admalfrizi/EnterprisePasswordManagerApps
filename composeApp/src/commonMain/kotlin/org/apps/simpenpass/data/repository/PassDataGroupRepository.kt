package org.apps.simpenpass.data.repository

import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataGroupSources
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.utils.NetworkResult

class PassDataGroupRepository(
    private val remotePassDataGroupSources: RemotePassDataGroupSources,
    private val localData : LocalStoreData
) {
    fun listPassDataGroup(
        groupId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.listGroupPassword(token, groupId)

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
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun listPassDataGroupRoleFiltered(
        groupId: Int,
        roleId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.listGroupPasswordRoleFiltered(token,groupId,roleId)

                when(result.success){
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }
                }
            }
        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun addPassDataGroup(
        groupId: Int,
        roleId: Int,
        addPassDataGroup: PassDataRequest
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.addPassGroup(token,groupId,roleId,addPassDataGroup)
                when(result.success){
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        if(result.code == 400){
                            emit(NetworkResult.Error("Maaf anda harus masukan data yang valid !"))
                        } else if(result.code == 404){
                            emit(NetworkResult.Error("Maaf Role di grup ini Tidak Tersedia !"))
                        } else {
                            emit(NetworkResult.Error(result.message))
                        }
                    }
                }
            }
        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }

    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun getPassDataGroupById(
        groupId: Int,
        passDataGroupId: Int?,
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.getDataPassGroupById(token,groupId,passDataGroupId!!)
                when(result.success){
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        if(result.code == 400){
                            emit(NetworkResult.Error("Maaf anda harus masukan data yang valid !"))
                        } else if(result.code == 404){
                            emit(NetworkResult.Error("Maaf Role di grup ini Tidak Tersedia !"))
                        } else {
                            emit(NetworkResult.Error(result.message))
                        }
                    }
                }
            }
        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }
}