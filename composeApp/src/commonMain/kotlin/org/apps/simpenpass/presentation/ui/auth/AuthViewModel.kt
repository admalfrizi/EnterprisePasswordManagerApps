package org.apps.simpenpass.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.utils.NetworkResult

class AuthViewModel(
    private val repo : UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> get() = _authState

    init {
        viewModelScope.launch {
            repo.getToken().collect { token ->
                _authState.update {
                    it.copy(
                        token = token
                    )
                }
            }

        }
    }

    fun login(data: LoginRequest){
        viewModelScope.launch {
            repo.login(data).collect { result ->
                when(result) {
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _authState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                userData = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun register(data: RegisterRequest){
        viewModelScope.launch {
            repo.register(data).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _authState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isRegistered = true,
                                userData = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}

data class AuthState (
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val isLoggedIn: Boolean = false,
    val userData: UserData? = null,
    val token: String? = null,
    val error: String? = null
)