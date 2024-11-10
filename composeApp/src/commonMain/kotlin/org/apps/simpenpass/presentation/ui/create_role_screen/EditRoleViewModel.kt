package org.apps.simpenpass.presentation.ui.create_role_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.utils.NetworkResult

class EditRoleViewModel(
    private val repoMemberGroup: MemberGroupRepository
) : ViewModel() {
    private val _editRole = MutableStateFlow(EditRoleState())
    val editRoleState = _editRole.asStateFlow()

    init {
        getMemberDataGroup(editRoleState.value.groupId!!)
    }

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
}

data class EditRoleState(
    val listMember: List<MemberGroupData>? = emptyList(),
    val groupId: String? = null,
    val msg: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)