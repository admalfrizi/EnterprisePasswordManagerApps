package org.apps.simpenpass.presentation.ui.create_role_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.AddRoleRequest
import org.apps.simpenpass.models.request.UpdateRoleMemberGroupRequest
import org.apps.simpenpass.utils.NetworkResult

class EditRoleViewModel(
    private val repoMemberGroup: MemberGroupRepository,
    private val repoGroup: GroupRepository,
) : ViewModel() {
    private val _editRoleState = MutableStateFlow(EditRoleState())
    val editRoleState = _editRoleState.asStateFlow()

    private val _editRoleMemberState = MutableStateFlow(UpdateRoleMemberState())
    val editRoleMemberState = _editRoleMemberState.asStateFlow()

    private val _roleState = MutableStateFlow(ListRoleState())
    val roleState = _roleState.asStateFlow()

    private val _memberState = MutableStateFlow(ListMemberState())
    val memberState = _memberState.asStateFlow()

    fun getMemberDataGroup(groupId: String) {
        viewModelScope.launch {
            repoMemberGroup.getMemberGroup(groupId.toInt()).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _memberState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _memberState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _memberState.update {
                            it.copy(
                                isLoading = false,
                                listMember = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getListRolePositionData(groupId: String) {
        viewModelScope.launch {
            repoGroup.listRoleGroup(groupId.toInt()).flowOn(Dispatchers.IO).collectLatest { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _roleState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _roleState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _roleState.update {
                            it.copy(
                                isLoading = false,
                                listRoleMember = res.data.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun addRoleGroup(role: AddRoleRequest,groupId: String){
        viewModelScope.launch {
            repoGroup.addRoleGroup(role,groupId.toInt()).flowOn(Dispatchers.IO).collectLatest { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _editRoleState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _editRoleState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _editRoleState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                                msg = res.data.message
                            )
                        }
                    }
                }
            }
        }
    }
//
    fun updateRoleMemberGroup(groupId: String, updateRoleMember: UpdateRoleMemberGroupRequest) {
        viewModelScope.launch {
            repoMemberGroup.updateRoleMemberGroup(groupId.toInt(),updateRoleMember).flowOn(
                Dispatchers.IO).collectLatest { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _editRoleMemberState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _editRoleMemberState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _editRoleMemberState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                                msg = res.data.message
                            )
                        }
                    }
                }
            }
        }
    }
}

data class ListRoleState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val msg: String? = null,
    val isError: Boolean = false,
    val listRoleMember: List<RoleGroupData>? = emptyList(),
)

data class ListMemberState(
    val listMember: List<MemberGroupData>? = emptyList(),
    val msg: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
)

data class EditRoleState(
    val msg: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
)

data class UpdateRoleMemberState(
    val msg: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
)