package org.apps.simpenpass.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.AuthRepository
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.utils.NetworkResult

class AuthViewModel(
    private val repo : AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState

    fun login(data: LoginRequest){
        viewModelScope.launch {
            try {
                repo.login(data).collect { result ->
                    when(result) {
                        is NetworkResult.Error -> {
                            _authState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoggedIn = true,
                                    error = null
                                )
                            }
                        }
                        is NetworkResult.Loading -> {
                            _authState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoggedIn = true,
                                    error = null
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _authState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoggedIn = true,
                                    error = null
                                )
                            }
                        }
                    }

                }
            } catch (e: Exception){
                _authState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        error = e.message
                    )
                }
            }
        }
    }
}

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)