package org.apps.simpenpass.data.repository

import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import org.apps.simpenpass.data.source.remoteData.RemoteMemberDataSources
import org.apps.simpenpass.data.source.remoteData.RemoteRolePositionGroup
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.AddGroupSecurityDataRequest
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.request.AddRoleRequest
import org.apps.simpenpass.models.request.UpdateRoleNameRequest
import org.apps.simpenpass.models.request.VerifySecurityDataGroupRequest
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
        memberList: List<AddMemberRequest>
    ) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.createGroup(token,insertData,imgName!!,imgFile)

                when(result.success){
                    true -> {
                        withContext(NonCancellable){
                            val addMember = remoteMemberDataSources.addMemberToGroup(token,memberList,result.data?.id!!)
                            if(addMember.success){
                                emit(NetworkResult.Success(result))
                            }
                        }
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

    fun detailGroup(groupId : Int,userId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.detailGroupData(token,groupId,userId)
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
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun addRoleGroup(role: AddRoleRequest,groupId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteRolePositionGroup.addRolePositionInGroup(token,role,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun deleteRoleGroup(roleId: Int,groupId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteRolePositionGroup.deleteRolePosition(token,groupId,roleId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun detailsRoleGroup(roleId: Int) = flow {
        emit(NetworkResult.Loading())
        try {
            localData.getToken.collect { token ->
                val result = remoteRolePositionGroup.detailRoleData(token,roleId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
                    }
                }
            }
        } catch (e: UnresolvedAddressException){
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Unknown Error"))
    }

    fun updateRoleNamePosition(roleId: Int, updateRoleName: UpdateRoleNameRequest) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteRolePositionGroup.updateRoleNamePosition(token,roleId,updateRoleName)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun getTypeSecurityGroupData() = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.getTypeSecurityGroup(token)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun getGroupSecurityData(
        groupId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.getGroupSecurityData(token,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun addGroupSecurityData(
        addGroupSecurityDataRequest: AddGroupSecurityDataRequest,
        groupId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.addGroupSecurityData(token,addGroupSecurityDataRequest,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun updateGroupSecurityData(
        addGroupSecurityDataRequest: AddGroupSecurityDataRequest,
        groupId: Int,
        id: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.updateGroupSecurityData(token,addGroupSecurityDataRequest,id,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun deleteGroupSecurityData(
        groupId: Int,
        id: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.deleteGroupSecurityData(token,id,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun verifySecurityData(
        groupId: Int,
        formVerifySecurityDataGroupRequest: VerifySecurityDataGroupRequest
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.verifySecurityDataInGroup(token,groupId,formVerifySecurityDataGroupRequest)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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

    fun getGroupById(
        groupId: Int
    ) = flow {
        try {
            localData.getToken.collect { token ->
                val result = remoteGroupSources.getGroupById(token,groupId)

                when(result.success) {
                    true -> {
                        emit(NetworkResult.Success(result))
                    }
                    false -> {
                        emit(NetworkResult.Error(result.message))
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
}