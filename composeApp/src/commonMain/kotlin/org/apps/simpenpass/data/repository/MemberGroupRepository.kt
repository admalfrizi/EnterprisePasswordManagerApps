package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteMemberDataSources
import org.apps.simpenpass.utils.NetworkResult

class MemberGroupRepository(
    private val remoteMemberDataSources : RemoteMemberDataSources,
    private val localData : LocalStoreData
) {
    fun getMemberGroup(groupId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteMemberDataSources.listUserJoinedInGroup(token, groupId)
                if (result.success) {
                    emit(NetworkResult.Success(result))
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error("No Internet Connection"))
        }
    }.catch { error ->
        emit(NetworkResult.Error(error.message ?: "Unknown Error"))
        Napier.v("Error Member : ${error.message}")
    }

}