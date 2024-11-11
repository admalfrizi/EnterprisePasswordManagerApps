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
import org.apps.simpenpass.utils.NetworkResult

class EditRoleViewModel(
    private val repoMemberGroup: MemberGroupRepository,
    private val repoGroup: GroupRepository,
) : ViewModel() {
    private val _editRole = MutableStateFlow(EditRoleState())
    val editRoleState = _editRole.asStateFlow()

    fun getMemberDataGroup(groupId: String) {
        viewModelScope.launch {
            repoMemberGroup.getMemberGroup(groupId.toInt()).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _editRole.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _editRole.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _editRole.update {
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
                        _editRole.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _editRole.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _editRole.update {
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
}

data class EditRoleState(
    val listMember: List<MemberGroupData>? = emptyList(),
    val listRoleMember: List<RoleGroupData>? = emptyList(),
    val msg: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)