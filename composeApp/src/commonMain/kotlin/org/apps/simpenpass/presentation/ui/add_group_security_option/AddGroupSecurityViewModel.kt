package org.apps.simpenpass.presentation.ui.add_group_security_option

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
import org.apps.simpenpass.models.pass_data.GroupSecurityData
import org.apps.simpenpass.models.response.GroupSecurityTypeResponse
import org.apps.simpenpass.utils.NetworkResult

class AddGroupSecurityViewModel(
    private val repo: GroupRepository,
) : ViewModel() {

    private val _groupSecurityDataState = MutableStateFlow(GroupSecurityDataState())
    val groupSecurityDataState = _groupSecurityDataState.asStateFlow()

    fun getTypeSecurityForGroup() {
        viewModelScope.launch {
            repo.getTypeSecurityGroupData().flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupSecurityDataState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupSecurityDataState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupSecurityDataState.update {
                            it.copy(
                                isLoading = false,
                                listTypeSecurityData = res.data.data!!
                            )
                        }
                    }
                }
            }
        }
    }


    fun getDataSecurityForGroup(groupId: Int) {
        viewModelScope.launch {
            repo.getGroupSecurityData(groupId).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupSecurityDataState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupSecurityDataState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupSecurityDataState.update {
                            it.copy(
                                isLoading = false,
                                listSecurityData = res.data.data!!
                            )
                        }
                    }
                }
            }
        }
    }
}

data class GroupSecurityDataState(
    val isLoading: Boolean = false,
    val msg : String? = null,
    val isError: Boolean = false,
    val listSecurityData: List<GroupSecurityData> = emptyList(),
    val listTypeSecurityData: List<GroupSecurityTypeResponse> = emptyList()
)