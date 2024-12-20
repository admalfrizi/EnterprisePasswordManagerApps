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
import org.apps.simpenpass.data.repository.PassDataGroupRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GroupSecurityData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.SendDataPassToDecrypt
import org.apps.simpenpass.models.request.VerifySecurityDataGroupRequest
import org.apps.simpenpass.models.response.GetPassDataEncrypted
import org.apps.simpenpass.utils.NetworkResult

class GroupSettingsViewModel(
    private val repo: GroupRepository,
    private val userRepo: UserRepository,
    private val repoPassGroup: PassDataGroupRepository,
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
            repo.detailGroup(groupId?.toInt()!!,userRepo.getUserData().id!!).collect { res ->
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


    fun getSecurityData() {
        viewModelScope.launch {
            repo.getGroupSecurityData(groupSettingsState.value.groupId?.toInt()!!).flowOn(
                Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                msg = res.error,
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
                                dataSecurity = res.data.data,
                            )
                        }
                    }
                }
            }
        }
    }

    fun verifyPassForDecrypt(
        verifySecurityDataGroupRequest: VerifySecurityDataGroupRequest,
    ) {
        viewModelScope.launch {
            repo.verifySecurityData(groupSettingsState.value.groupId?.toInt()!!,verifySecurityDataGroupRequest ).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupSettingsState.update {
                            it.copy(
                                isLoading = false,
                                isPassVerify = res.data.data!!,
                                key = if(res.data.data) verifySecurityDataGroupRequest.securityValue else ""
                            )
                        }
                    }
                }
            }
        }

    }

    fun getPassDataGroupEncrypted(groupId: String?){
        viewModelScope.launch {
            repo.getPassDataEncrypted(groupId?.toInt()!!).flowOn(Dispatchers.IO).collect { res ->
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
                                passDataGroup = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun sendDataPassToDecrypt(groupId: String, sendDataPassToDecrypt: SendDataPassToDecrypt){
        viewModelScope.launch {
            repo.sendDataPassToDecrypt(groupId.toInt(), sendDataPassToDecrypt).flowOn(Dispatchers.IO).collect { res ->
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
                                isDecrypted = true,
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
    var isDecrypted: Boolean = false,
    val groupId: String? = null,
    val msg : String? = null,
    var key : String? = null,
    val isError: Boolean = false,
    val groupData: DtlGrupPass? = null,
    var isPassVerify : Boolean = false,
    var passDataGroup: List<GetPassDataEncrypted> = emptyList(),
    val dataSecurity : GroupSecurityData? = null,
    val listSecurityData: List<GroupSecurityData> = emptyList()
)