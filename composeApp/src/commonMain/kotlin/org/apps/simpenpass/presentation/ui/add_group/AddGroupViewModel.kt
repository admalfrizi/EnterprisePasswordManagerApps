package org.apps.simpenpass.presentation.ui.add_group

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
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.utils.NetworkResult

class AddGroupViewModel(
    private val repoMember: MemberGroupRepository,
    private val groupRepo: GroupRepository,
): ViewModel() {
    private val _addGroupState = MutableStateFlow(AddGroupState())
    val addGroupState = _addGroupState.asStateFlow()

    init {
        addCurrentUserToMemberGroup()
    }

    fun addGroup(
        insertData: AddGroupRequest,
        imgFile: ByteArray?,
        imgName : String?
    ){
        viewModelScope.launch {
            groupRepo.createGroup(insertData,imgName,imgFile,addGroupState.value.memberListAdd).flowOn(Dispatchers.IO).collect{ result ->
                when(result){
                    is NetworkResult.Error -> {
                        _addGroupState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _addGroupState.update {
                            it.copy(
                                isLoading = true,
                                isError = false,
                                msg = ""
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _addGroupState.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                isCreated = true,
                                grupData = result.data.data!!,
                                msg = null
                            )
                        }
                    }
                }
            }
        }
    }

     private fun addCurrentUserToMemberGroup(){
        viewModelScope.launch {
            val itemData = repoMember.getUserData()
            val addItem = AddMemberRequest(itemData.id!!,true)
            val addMember = UserData(itemData.id, itemData.name!!, itemData.email!!)

            _addGroupState.update { currentList ->
                currentList.copy(
                    memberList = currentList.memberList!! + addMember,
                    memberListAdd = currentList.memberListAdd + addItem
                )
            }
        }
    }

    fun addMemberToList(member: List<UserData>){
        viewModelScope.launch {
            _addGroupState.update { currentList ->
                val toAddMemberRequestList = mutableListOf<AddMemberRequest>()
                member.forEach {
                   toAddMemberRequestList.add(AddMemberRequest(it.id,isGroupAdmin = false))
                }

                currentList.copy(
                    memberList = currentList.memberList!! + member,
                    memberListAdd = currentList.memberListAdd + toAddMemberRequestList,
                )
            }
        }
    }

//    private fun addMemberToDb(
//        memberList: List<AddMember>,
//        groupId: Int,
//    ) {
//        viewModelScope.launch {
//            repoMember.addUsersToJoinGroup(memberList,groupId).flowOn(Dispatchers.IO).collect { res ->
//                when(res){
//                    is NetworkResult.Error -> {
//                        _addGroupState.update {
//                            it.copy(
//                                isLoading = false,
//                                isError = true,
//                                msgAddMember = res.error
//                            )
//                        }
//                    }
//                    is NetworkResult.Loading -> {
//                        _addGroupState.update {
//                            it.copy(
//                                isLoading = true,
//                                isError = false,
//                            )
//                        }
//                    }
//                    is NetworkResult.Success -> {
//                        _addGroupState.update {
//                            it.copy(
//                                isLoading = false,
//                                isError = false,
//                                isAdded = true,
//                                msgAddMember = res.data.message
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun findMemberForAddToGroup(query: String){
        viewModelScope.launch {
            repoMember.findUsersToJoinedGroup(query).collect{ result ->
                when(result){
                    is NetworkResult.Error -> {
                        _addGroupState.update { currentList ->
                            currentList.copy(
                                isLoading = false,
                                isError = true,
                                msg = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _addGroupState.update { currentList ->
                            currentList.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _addGroupState.update { currentList ->
                            currentList.copy(
                                isLoading = false,
                                isError = false,
                                searchUserData = result.data.data?.data,
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearSearchData(){
        viewModelScope.launch {
            _addGroupState.update {
                it.copy(
                    searchUserData = emptyList()
                )
            }
        }
    }

}

data class AddGroupState(
    val memberListAdd: List<AddMemberRequest> = emptyList(),
    var memberList: List<UserData>? = emptyList(),
    val isCreated: Boolean = false,
    val isAdded: Boolean = false,
    val grupData: GrupPassData? = null,
    val searchUserData : List<UserData>? = emptyList(),
    val msg: String? = null,
    val msgAddMember: String? = null,
    val isLoading: Boolean = false,
    var isError: Boolean = false,
)