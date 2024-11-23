package org.apps.simpenpass.presentation.ui.group_pass.edit_anggota_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.request.UpdateAdminMemberGroupRequest
import org.apps.simpenpass.utils.NetworkResult

class EditAnggotaGroupViewModel(
    private val repo: MemberGroupRepository
) : ViewModel() {
    private val _editAnggotaState = MutableStateFlow(EditAnggotaState())
    val editAnggotaState = _editAnggotaState.asStateFlow()

    fun updateAdminMember(
        groupId: String,
        listUpdateMember: List<UpdateAdminMemberGroupRequest>
    ) {
        viewModelScope.launch {
            repo.updateAdminMemberGroup(groupId.toInt(),listUpdateMember).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _editAnggotaState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _editAnggotaState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _editAnggotaState.update {
                            it.copy(
                                isLoading = false,
                                isUpdated = true,
                            )
                        }
                    }
                }
            }
        }
    }
}

data class EditAnggotaState(
    var msg: String = "",
    var isUpdated: Boolean = false,
    var isError: Boolean = false,
    var isLoading: Boolean = false,
)