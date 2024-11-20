package org.apps.simpenpass.presentation.ui.group_pass.pass_data_group_detail

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
import org.apps.simpenpass.data.repository.PassDataGroupRepository
import org.apps.simpenpass.models.response.PassDataGroupByIdResponse
import org.apps.simpenpass.utils.NetworkResult

class PassDataDetailsViewModel(
    private val repoPassDataGroup: PassDataGroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val passDataGroupId = savedStateHandle.get<String>("passDataGroupId")
    private val groupId = savedStateHandle.get<String>("groupId")

    private val _passDataDtlState = MutableStateFlow(PassDataDetailsState())
    val passDataDtlState = _passDataDtlState.asStateFlow()


    init {
        _passDataDtlState.update {
            it.copy(
                passDataGroupId = passDataGroupId?.toInt(),
                groupId = groupId?.toInt()
            )
        }

        viewModelScope.launch {
            repoPassDataGroup.getPassDataGroupById(_passDataDtlState.value.groupId!!, _passDataDtlState.value.passDataGroupId)
                .flowOn(Dispatchers.IO).collect { res ->
                    when(res){
                        is NetworkResult.Error -> {
                            _passDataDtlState.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true
                                )
                            }
                        }
                        is NetworkResult.Loading -> {
                            _passDataDtlState.update {
                                it.copy(
                                    isLoading = true,
                                    isError = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _passDataDtlState.update {
                                it.copy(
                                    isLoading = false,
                                    passData = res.data.data
                                )
                            }
                        }
                    }
                }
        }
    }

    fun clearState(){
        _passDataDtlState.update {
            it.copy(
                passData = null
            )
        }
    }
}

data class PassDataDetailsState(
    val passDataGroupId : Int? = null,
    val groupId : Int? = null,
    val passData: PassDataGroupByIdResponse? = null,
    val isLoading : Boolean? = false,
    val isError : Boolean? = false
)