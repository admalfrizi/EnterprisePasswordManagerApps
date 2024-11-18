package org.apps.simpenpass.presentation.ui.create_data_pass.group

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
import org.apps.simpenpass.models.pass_data.AddContentPassData
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult

class FormPassGroupViewModel(
    private val repoPassDataGroup: PassDataGroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _formPassGroupDataState = MutableStateFlow(FormPassGroupState())
    val formPassDataGroupState = _formPassGroupDataState.asStateFlow()

    private val passDataGroupId = savedStateHandle.get<String>("passDataGroupId")

    init {
        _formPassGroupDataState.update {
            it.copy(
                passDataGroupId = passDataGroupId
            )
        }
    }

    fun createPassData(
        formData: PassDataRequest,
        groupId: String,
        roleId: Int
    ) {
        viewModelScope.launch {
            repoPassDataGroup.addPassDataGroup(groupId.toInt(),roleId,formData).flowOn(Dispatchers.IO)
                .collect { res ->
                    when(res){
                        is NetworkResult.Error -> {
                            _formPassGroupDataState.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.error
                                )
                            }
                        }
                        is NetworkResult.Loading -> {
                            _formPassGroupDataState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _formPassGroupDataState.update {
                                it.copy(
                                    isLoading = false,
                                    isCreated = res.data.success,
                                    msg = res.data.message
                                )
                            }
                        }
                    }
                }
        }
    }

    fun updatePassData(){
        viewModelScope.launch {

        }
    }
}


data class FormPassGroupState(
    val isLoading : Boolean = false,
    val passData: PassResponseData? = null,
    val passDataGroupId: String? = null,
    val insertAddContentPassData: List<InsertAddContentDataPass> = emptyList(),
    val listAddContentPassData: List<AddContentPassData> = emptyList(),
    val isCreated: Boolean = false,
    val isUpdated: Boolean = false,
    val error : String? = null,
    val msg : String? = null,
    val msgAddContentData: String? = null
)