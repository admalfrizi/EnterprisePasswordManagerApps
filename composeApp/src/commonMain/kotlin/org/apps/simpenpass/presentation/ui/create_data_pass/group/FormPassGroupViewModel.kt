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
import org.apps.simpenpass.models.pass_data.AddContentPassDataGroup
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.FormAddContentPassDataGroup
import org.apps.simpenpass.models.request.PassDataGroupRequest
import org.apps.simpenpass.models.response.PassDataGroupByIdResponse
import org.apps.simpenpass.utils.NetworkResult

class FormPassGroupViewModel(
    private val repoPassDataGroup: PassDataGroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _formPassGroupDataState = MutableStateFlow(FormPassGroupState())
    val formPassDataGroupState = _formPassGroupDataState.asStateFlow()

    private val passDataGroupId = savedStateHandle.get<String>("passDataGroupId")
    private val groupId = savedStateHandle.get<String>("groupId")

    init {
        _formPassGroupDataState.update {
            it.copy(
                passDataGroupId = passDataGroupId,
                groupId = groupId
            )
        }

        if(_formPassGroupDataState.value.passDataGroupId != "{passDataGroupId}" && _formPassGroupDataState.value.passDataGroupId != "-1"){
            loadDataPassById(_formPassGroupDataState.value.passDataGroupId?.toInt(),_formPassGroupDataState.value.groupId!!)
        }
    }

    fun createPassData(
        formData: PassDataGroupRequest,
        groupId: String
    ) {
        viewModelScope.launch {
            repoPassDataGroup.addPassDataGroup(groupId.toInt(),formData).flowOn(Dispatchers.IO)
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

    fun getListRoleData(groupId: String){
        viewModelScope.launch {
            repoPassDataGroup.getListRoleData(groupId.toInt()).flowOn(Dispatchers.IO).collect { res ->
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
                                listRoleData = res.data.data!!
                            )
                        }
                    }
                }
            }
        }
    }

    fun updatePassData(
        groupId: String,
        passGroupDataId: String,
        updateFormData: PassDataGroupRequest,
        updateAddContentPassDataGroup: List<FormAddContentPassDataGroup>,
        deleteAddContentPassDataGroup: List<FormAddContentPassDataGroup>
    ){
        viewModelScope.launch {
            repoPassDataGroup.updatePassDataGroupById(groupId.toInt(),passGroupDataId.toInt(),updateFormData,deleteAddContentPassDataGroup,updateAddContentPassDataGroup).flowOn(
                Dispatchers.IO).collect { res ->
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
                                isUpdated = true,
                                msg = res.data.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadDataPassById(passDataGroupId: Int?, groupId: String) {
        viewModelScope.launch {
            repoPassDataGroup.getPassDataGroupById(groupId.toInt(),passDataGroupId?.toInt()).collect { result ->
                when(result){
                    is NetworkResult.Error -> {
                        _formPassGroupDataState.update {
                            it.copy(
                                error = result.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _formPassGroupDataState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _formPassGroupDataState.update {
                            it.copy(
                                isLoading = false,
                                passData = result.data.data,
                                listAddContentPassData = result.data.data?.addPassContent!!
                            )
                        }
                    }
                }
            }
        }
    }
}


data class FormPassGroupState(
    val isLoading : Boolean = false,
    val passData: PassDataGroupByIdResponse? = null,
    val passDataGroupId: String? = null,
    val listAddContentPassData: List<AddContentPassDataGroup> = emptyList(),
    val listRoleData: List<RoleGroupData> = emptyList(),
    var isCreated: Boolean = false,
    var isUpdated: Boolean = false,
    val groupId: String? = null,
    val error : String? = null,
    val msg : String? = null,
    val msgAddContentData: String? = null
)