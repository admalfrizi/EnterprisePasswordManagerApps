package org.apps.simpenpass.data.repository

import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataGroupSources
import org.apps.simpenpass.data.source.remoteData.RemoteRolePositionGroup
import org.apps.simpenpass.models.request.FormAddContentPassDataGroup
import org.apps.simpenpass.models.request.PassDataGroupRequest
import org.apps.simpenpass.utils.NetworkResult

class PassDataGroupRepository(
    private val remotePassDataGroupSources: RemotePassDataGroupSources,
    private val remoteRolePositionGroup: RemoteRolePositionGroup,
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
        addPassDataGroup: PassDataGroupRequest
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.addPassGroup(token,groupId,addPassDataGroup)
                when(result.success){
                    true -> {
                        if(addPassDataGroup.addPassContent != null && result.data?.id != null){
                            remotePassDataGroupSources.addContentPassData(token,groupId,result.data.id,addPassDataGroup.addPassContent)
                        }
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

    fun getListRoleData(
        groupId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteRolePositionGroup.listRolePositionInGroup(token,groupId)
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

    fun updatePassDataGroupById(
        groupId: Int,
        passDataGroupId: Int?,
        updatePassData: PassDataGroupRequest,
        deleteListAddContentPass: List<FormAddContentPassDataGroup>,
        updateListAddContentPass: List<FormAddContentPassDataGroup>,
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.updatePassGroup(token,groupId,passDataGroupId!!,updatePassData)
                when(result.success){
                    true -> {
                        if(updatePassData.addPassContent != null && result.data?.id != null){
                            remotePassDataGroupSources.addContentPassData(token,groupId,passDataGroupId,updatePassData.addPassContent)
                        }

                        if(deleteListAddContentPass.isNotEmpty()){
                            remotePassDataGroupSources.deleteAddContentPassData(token,passDataGroupId,deleteListAddContentPass)
                        }

                        if(updateListAddContentPass.isNotEmpty()){
                            remotePassDataGroupSources.updateAddContentPassData(token,passDataGroupId,updateListAddContentPass)
                        }

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
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun deletePassDataGroup(
        passDataGroupId: Int,
        groupId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.deletePassDataGroup(token,passDataGroupId,groupId)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.onStart {
        emit(NetworkResult.Loading())
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }
}