package org.apps.simpenpass.presentation.ui.add_group

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.request.AddMemberRequest

class AddGroupViewModel(
    private val repoMember: MemberGroupRepository,
): ViewModel() {
    private val _addGroupState = MutableStateFlow(AddGroupState())
    val addGroupState = _addGroupState.asStateFlow()


}

data class AddGroupState(
    val memberListAdd: AddMemberRequest? = null,
)