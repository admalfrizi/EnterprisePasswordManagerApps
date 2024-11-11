package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import org.apps.simpenpass.data.source.remoteData.RemoteMemberDataSources
import org.apps.simpenpass.data.source.remoteData.RemoteRolePositionGroup
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.AddMember
import org.apps.simpenpass.utils.NetworkResult

class GroupRepository(
    private val remoteGroupSources : RemoteGroupDataSources,
    private val remoteMemberDataSources: RemoteMemberDataSources,
    private val remoteRolePositionGroup: RemoteRolePositionGroup,
    private val localData : LocalStoreData
) {
    fun createGroup(
        insertData: AddGroupRequest,
        imgName: String?,
        imgFile: ByteArray?,
        memberList: List<AddMember>
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.createGroup(token,insertData,imgName!!,imgFile)

                when(result.success){
                    true -> {
                        remoteMemberDataSources.addMemberToGroup(token,memberList,result.data?.id!!)
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
        Napier.v("Error Add Group : ${it.message}")
    }

    fun listJoinedGrup() = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val userId = localData.getUserData().id
                val result = remoteGroupSources.listJoinedGroupBasedOnUser(token,userId!!)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }

        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun detailGroup(groupId : Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.detailGroupData(token,groupId)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }

        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun updateGroup(
        groupId : Int,
        editGroupData: AddGroupRequest,
        imgName: String?,
        imgFile: ByteArray?,
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.updateGroupData(token,groupId,editGroupData,imgName!!,imgFile)
                if(result.success){
                    emit(NetworkResult.Success(result))
                }
            }
        }catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun searchGroup(query: String) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.searchGroup(token,query)
                if(result.success){
                    emit(NetworkResult.Success(result))
                } else {
                    emit(NetworkResult.Error(result.message))
                }
                Napier.v("Data Search Group : ${result.data}")
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
        Napier.v("Error Search Group : ${it.message}")
    }

    fun listRoleGroup(groupId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteRolePositionGroup.listRolePositionInGroup(token,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }
                }
                Napier.v("Data Role Group : ${result.data}")
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

}