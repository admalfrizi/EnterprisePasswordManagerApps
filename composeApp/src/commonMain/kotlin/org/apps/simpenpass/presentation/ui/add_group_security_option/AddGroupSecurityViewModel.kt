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
import org.apps.simpenpass.models.request.AddGroupSecurityDataRequest
import org.apps.simpenpass.models.request.SendDataPassToDecrypt
import org.apps.simpenpass.models.response.GetPassDataGroup
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
                                securityData = res.data.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun addSecurityDataForGroup(
        addGroupSecurityDataRequest: AddGroupSecurityDataRequest,
        groupId: Int
    ) {
        viewModelScope.launch {
            repo.addGroupSecurityData(addGroupSecurityDataRequest,groupId).flowOn(Dispatchers.IO).collect { res ->
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
                                isAdded = true,
                                msg = res.data.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateSecurityDataForGroup(
        addGroupSecurityDataRequest: AddGroupSecurityDataRequest,
        groupId: Int,
        id: Int
    ) {
        viewModelScope.launch {
            repo.updateGroupSecurityData(addGroupSecurityDataRequest,groupId,id).flowOn(Dispatchers.IO).collect { res ->
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
                                isUpdated = true,
                                msg = res.data.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteSecurityDataForGroup(
        groupId: Int,
        id: Int
    ) {
        viewModelScope.launch {
            repo.deleteGroupSecurityData(groupId,id).flowOn(Dispatchers.IO).collect { res ->
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
                                isDeleted = true,
                                msg = res.data.message
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
                                passDataGroup = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

//    fun getPassDataGroupDecrypted(groupId: String?){
//        viewModelScope.launch {
//            repo.getPassDataDecrypted(groupId?.toInt()!!).flowOn(Dispatchers.IO).collect { res ->
//                when(res) {
//                    is NetworkResult.Error -> {
//                        _groupSecurityDataState.update {
//                            it.copy(
//                                isLoading = false,
//                                isError = true,
//                                msg = res.error
//                            )
//                        }
//                    }
//                    is NetworkResult.Loading -> {
//                        _groupSecurityDataState.update {
//                            it.copy(
//                                isLoading = true,
//                            )
//                        }
//                    }
//                    is NetworkResult.Success -> {
//                        _groupSecurityDataState.update {
//                            it.copy(
//                                isLoading = false,
//                                passDataGroupDec = res.data.data!!,
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun sendGroupDataPassToDecrypt(sendDataPassToDecrypt: SendDataPassToDecrypt, groupId: Int){
        viewModelScope.launch {
            repo.sendDataPassToDecrypt(groupId,sendDataPassToDecrypt).flowOn(Dispatchers.IO).collect { res ->
                when(res) {
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
                                isSent = true,
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
    var isDeleted: Boolean = false,
    var isAdded: Boolean = false,
    var isUpdated: Boolean = false,
    var isSent: Boolean = false,
    var passDataGroup: List<GetPassDataGroup> = emptyList(),
    var securityData: GroupSecurityData? = null,
    val listTypeSecurityData: List<GroupSecurityTypeResponse> = emptyList()
)