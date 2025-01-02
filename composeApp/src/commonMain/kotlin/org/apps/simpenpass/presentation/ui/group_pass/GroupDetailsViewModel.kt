package org.apps.simpenpass.presentation.ui.group_pass

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.data.repository.PassDataGroupRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GroupSecurityData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.pass_data.PassDataGroup
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.VerifySecurityDataGroupRequest
import org.apps.simpenpass.utils.NetworkResult

class GroupDetailsViewModel(
    private val repoGroup: GroupRepository,
    private val userRepo: UserRepository,
    private val repoPassDataGroup: PassDataGroupRepository,
    private val repoMemberGroup: MemberGroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val groupId = savedStateHandle.get<String>("groupId")
    private val passDataGroupId = savedStateHandle.get<String>("passDataGroupId")

    private val _groupDtlState = MutableStateFlow(GroupDetailsState())
    val groupDtlState = _groupDtlState.onStart {
        getDetailGroup(groupId!!)
        getRoleGroupList(groupId)
        getAllPassDataGroup(groupId)
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(),_groupDtlState.value)

    init {
        _groupDtlState.update {
            it.copy(
                groupId = groupId
            )
        }

        if(passDataGroupId?.isNotEmpty()!!){
            _groupDtlState.update {
                it.copy(
                    passDataGroupId = passDataGroupId
                )
            }
        }
    }

    fun getDetailGroup(groupId: String) {
        viewModelScope.launch {
            repoGroup.detailGroup(groupId.toInt(),userRepo.getUserData().id!!).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
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

    fun getMemberDataGroup(groupId: String) {
        viewModelScope.launch {
            repoMemberGroup.getMemberGroup(groupId.toInt()).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
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

    fun getRoleGroupList(groupId: String){
        viewModelScope.launch {
            repoGroup.listRoleGroup(groupId.toInt()).flowOn(Dispatchers.IO).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                listRoleGroup = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getAllPassDataGroup(groupId: String?){
        viewModelScope.launch {
            repoPassDataGroup.listPassDataGroup(groupId?.toInt()!!).flowOn(Dispatchers.IO).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                passDataGroup = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getPassDataGroupRoleFilter(groupId: String,roleId: Int){
        viewModelScope.launch(Dispatchers.Default){
            repoPassDataGroup.listPassDataGroupRoleFiltered(groupId.toInt(),roleId).flowOn(Dispatchers.IO).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                passDataGroup = res.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun deletePassDataGroup(
        passDataGroupId: Int
    ) {
        viewModelScope.launch {
            repoPassDataGroup.deletePassDataGroup(passDataGroupId,groupId?.toInt()!!).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isDeleted = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun getSecurityData() {
        viewModelScope.launch {
            repoGroup.getGroupSecurityData(groupDtlState.value.groupId?.toInt()!!).flowOn(
                Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                msg = res.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
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
            repoGroup.verifySecurityData(groupDtlState.value.groupId?.toInt()!!,verifySecurityDataGroupRequest ).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _groupDtlState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _groupDtlState.update {
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

    fun clearState() {
        _groupDtlState.value = GroupDetailsState()
    }
}

data class GroupDetailsState(
    var passDataGroup: List<PassDataGroup?> = emptyList(),
    val listRoleGroup: List<RoleGroupData?> = emptyList(),
    val dtlGroupData: DtlGrupPass? = null,
    val groupId: String? = null,
    var passDataGroupId: String? = null,
    var key : String? = null,
    var msg: String = "",
    val memberGroupData: List<MemberGroupData> = emptyList(),
    var isUpdated: Boolean = false,
    var isError: Boolean = false,
    var isLoading: Boolean = false,
    var isPassVerify : Boolean = false,
    var isDeleted : Boolean = false,
    val dataSecurity : GroupSecurityData? = null
)