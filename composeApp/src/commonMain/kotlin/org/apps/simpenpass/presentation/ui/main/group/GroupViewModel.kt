package org.apps.simpenpass.presentation.ui.main.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.utils.NetworkResult

class GroupViewModel(
    private val repoGroup: GroupRepository,
    private val repoMemberGroupRepository: MemberGroupRepository
): ViewModel() {
    private val _groupState = MutableStateFlow(GroupState())
    val groupState = _groupState.asStateFlow()

    init {
        viewModelScope.launch {
            repoGroup.listJoinedGrup().collect { res ->
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

    fun getMemberDataGroup(groupId: String) {
        viewModelScope.launch {
            repoMemberGroupRepository.getMemberGroup(groupId.toInt()).collect { res ->
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
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupState.update {
                            it.copy(
                                isLoading = false,
                                memberGroupData = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getDetailGroup(groupId: String) {
        viewModelScope.launch {
            repoGroup.detailGroup(groupId.toInt()).collect { res ->
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
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupState.update {
                            it.copy(
                                isLoading = false,
                                dtlGroupData = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }
}

data class GroupState(
    val groupData: List<GrupPassData?> = emptyList(),
    val dtlGroupData: DtlGrupPass? = null,
    val memberGroupData: List<MemberGroupData?> = emptyList(),
    val msg: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false
)