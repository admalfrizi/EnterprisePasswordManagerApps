package org.apps.simpenpass.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.remoteData.AuthApi
import org.apps.simpenpass.utils.NetworkResult

class AuthViewModel(
    private val client: HttpClient,
    private val repo : AuthApi
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.login(email, password).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
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
                                isLoading = true,
                                isLoggedIn = false,
                                error = null
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = false,
                                error = result.error.message
                            )
                        }
                    }
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