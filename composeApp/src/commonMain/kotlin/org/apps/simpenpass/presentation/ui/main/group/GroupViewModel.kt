package org.apps.simpenpass.presentation.ui.main.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.ResultSearchGroup
import org.apps.simpenpass.utils.NetworkResult

class GroupViewModel(
    private val repoGroup: GroupRepository,
    private val konnection: Konnection,
): ViewModel() {
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _groupState = MutableStateFlow(GroupState())
    val groupState = _groupState.asStateFlow()

    init {
        observeConnection()
    }

    private fun observeConnection() {
        viewModelScope.launch {
            konnection.observeHasConnection().collect { hasConnect ->
                _isConnected.value = hasConnect
                _groupState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getJoinedGroup(){
        viewModelScope.launch {
            repoGroup.listJoinedGrup().flowOn(Dispatchers.IO).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupState.update {
                            it.copy(
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupState.update {
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

    fun searchGroup(query: String) {
        viewModelScope.launch {
            repoGroup.searchGroup(query).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                searchGroupResult = res.data.data,
                            )
                        }
                    }
                }
            }
        }
    }


    fun clearState() {
        _groupState.value = GroupState()
    }
}

data class GroupState(
    var groupData: List<GrupPassData?> = emptyList(),
    var searchGroupResult: ResultSearchGroup? = null,
    var msg: String = "",
    var isError: Boolean = false,
    var isLoading: Boolean = false,
)