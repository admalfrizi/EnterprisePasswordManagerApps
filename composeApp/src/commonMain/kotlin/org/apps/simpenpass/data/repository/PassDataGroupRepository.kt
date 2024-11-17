package org.apps.simpenpass.data.repository

import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataGroupSources
import org.apps.simpenpass.utils.NetworkResult

class PassDataGroupRepository(
    private val remotePassDataGroupSources: RemotePassDataGroupSources,
    private val localData : LocalStoreData
) {
    fun listPassDataGroup(
        groupId: Int
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remotePassDataGroupSources.listGroupPassword(token,groupId)

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
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun listPassDataGroupRoleFiltered(
        groupId: Int,
        roleId: Int
    ) = flow {
        emit(NetworkResult.Loading())
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
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }
}