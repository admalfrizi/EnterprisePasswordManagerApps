package org.apps.simpenpass.presentation.ui.add_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.request.AddMember
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.utils.NetworkResult

class AddGroupViewModel(
    private val repoMember: MemberGroupRepository,
): ViewModel() {
    private val _addGroupState = MutableStateFlow(AddGroupState())
    val addGroupState = _addGroupState.asStateFlow()

    init {
        addCurrentUserToMemberGroup()
    }

     private fun addCurrentUserToMemberGroup(){
        viewModelScope.launch {
            val itemData = repoMember.getUserData()
            val addItem = AddMember(itemData.id!!,true)
            val addMember = UserData(itemData.id, itemData.name!!, itemData.email!!)

            _addGroupState.update { currentList ->
                currentList.copy(
                    memberList = currentList.memberList!! + addMember,
                    memberListAdd = currentList.memberListAdd!! + addItem
                )
            }
        }
    }

    fun addMemberToList(member: UserData){
        viewModelScope.launch {
            _addGroupState.update { currentList ->
                currentList.copy(
                    memberList = currentList.memberList!! + member,
                    memberListAdd = currentList.memberListAdd!! + AddMember(member.id,false)
                )
            }
        }
    }

    fun addMemberToDb(
        memberList: AddMemberRequest,
        groupId: String,
    ) {
        viewModelScope.launch {
            repoMember.addUsersToJoinGroup(memberList,groupId.toInt()).collect { res ->
                when(res){
                    is NetworkResult.Error -> TODO()
                    is NetworkResult.Loading -> TODO()
                    is NetworkResult.Success -> TODO()
                }
            }
        }
    }

    fun findMemberForAddToGroup(query: String){
        viewModelScope.launch {
            repoMember.findUsersToJoinedGroup(query).collect{ result ->
                when(result){
                    is NetworkResult.Error -> {
                        _addGroupState.update { currentList ->
                            currentList.copy(
                                isLoading = false,
                                isError = true,
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
                                searchUserData = result.data.data!!
                            )
                        }
                    }
                }
            }
        }
    }

}

data class AddGroupState(
    val memberListAdd: List<AddMember> = emptyList(),
    var memberList: List<UserData>? = emptyList(),
    val searchUserData : List<UserData>? = emptyList(),
    var isLoading: Boolean? = false,
    var isError: Boolean? = false,
)