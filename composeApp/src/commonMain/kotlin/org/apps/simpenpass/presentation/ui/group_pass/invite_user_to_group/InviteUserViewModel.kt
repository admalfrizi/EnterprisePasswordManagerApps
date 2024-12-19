package org.apps.simpenpass.presentation.ui.group_pass.invite_user_to_group

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
import org.apps.simpenpass.data.repository.MemberGroupRepository
import org.apps.simpenpass.models.request.SendEmailRequest
import org.apps.simpenpass.utils.NetworkResult

class InviteUserViewModel(
    private val repoMember: MemberGroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _inviteUserState = MutableStateFlow(InviteUserState())
    val inviteUserState = _inviteUserState.asStateFlow()

    private val groupId = savedStateHandle.get<String>("groupId")

    fun findEmailUser(
        query: String
    ) {
        viewModelScope.launch {
            repoMember.findEmailUser(query).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _inviteUserState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _inviteUserState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _inviteUserState.update {
                            it.copy(
                                isLoading = false,
                                isFound = true,
                                findResult = res.data.data
                            )
                        }
                    }
                }
            }
        }
    }
}

data class InviteUserState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    var isFound: Boolean = false,
    val msg: String? = null,
    val isError: Boolean = false,
    val findResult : SendEmailRequest? = null
)