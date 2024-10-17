package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import org.apps.simpenpass.utils.NetworkResult

class GroupRepository(
    private val remoteGroupSources : RemoteGroupDataSources,
    private val localData : LocalStoreData
) {
    fun listJoinedGrup() = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val userId = localData.getUserData().id
                val result = remoteGroupSources.listJoinedGroupBasedOnUser(token,userId!!)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
                Napier.v("Data Group : ${result.data}")
            }

        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
        Napier.v("Error Group : ${it.message}")
    }

    fun detailGroup(groupId : Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.detailGroupData(token,groupId)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
                Napier.v("Data Dtl Group : ${result.data}")
            }

        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
        Napier.v("Error Group : ${it.message}")
    }

}