package org.apps.simpenpass.presentation.ui.group_pass.settings_group

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.utils.NetworkResult

class GroupSettingsViewModel(
    private val repo: GroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _groupSettingsState = MutableStateFlow(GroupSettingsState())
    val groupSettingsState = _groupSettingsState.asStateFlow()

    private val groupId = savedStateHandle.get<String>("groupId")

    init {
        if(groupId != null && groupId != "{groupId}"){
            _groupSettingsState.update {
                it.copy(
                    groupId = groupId
                )
            }
            getDetailGroup()
        }
    }

    fun getDetailGroup() {
        viewModelScope.launch {
            repo.detailGroup(groupId?.toInt()!!).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                groupData = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateGroupData(
        groupId: String,
        updateGroupRequest: AddGroupRequest,
        imgFile: ByteArray?,
        imgName : String?
    ) {
        viewModelScope.launch {
            repo.updateGroup(groupId.toInt(),updateGroupRequest,imgName,imgFile).flowOn(Dispatchers.IO).collect{ res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
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

data class GroupSettingsState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val groupId: String? = null,
    val msg : String? = null,
    val isError: Boolean = false,
    val groupData: DtlGrupPass? = null
)