package org.apps.simpenpass.presentation.ui.add_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.request.AddMember
import org.apps.simpenpass.models.user_data.UserData

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

}

data class AddGroupState(
    val memberListAdd: List<AddMember>? = emptyList(),
    var memberList: List<UserData>? = emptyList(),
)