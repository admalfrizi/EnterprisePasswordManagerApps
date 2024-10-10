package org.apps.simpenpass.presentation.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.LocalUserStore
import org.apps.simpenpass.utils.NetworkResult

class ProfileViewModel(
    private val repo : UserRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> get() = _profileState

    init {
        viewModelScope.launch {
            _profileState.update {
                it.copy(
                    userData = repo.getUserData()
                )
            }
        }

        viewModelScope.launch {
            repo.getToken().collect { token ->
                _profileState.update {
                    it.copy(
                        token = token
                    )
                }
            }

        }
    }

    fun logout(token: String){
        viewModelScope.launch {
            repo.logout(token).collect { result ->
                Napier.v("Response Token: $token")
                when(result) {
                    is NetworkResult.Error -> {
                        _profileState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _profileState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _profileState.update {
                            it.copy(
                                isLoading = false,
                                isLogout = true,
                            )
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }
}

data class ProfileState(
    val isLogout: Boolean = false,
    val isLoading: Boolean = false,
    val userData: LocalUserStore? = null,
    val token: String? = null,
    val error: String? = null
)