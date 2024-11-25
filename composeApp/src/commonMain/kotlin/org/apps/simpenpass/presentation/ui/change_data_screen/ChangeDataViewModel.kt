package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.utils.NetworkResult

class ChangeDataViewModel(
    private val userRepo: UserRepository
): ViewModel() {
    private val _changeDataState = MutableStateFlow(ChangeDataState())
    val changeDataState = _changeDataState.asStateFlow()

    fun sendOtp(email: String){
        viewModelScope.launch {
            userRepo.changeDataOtp(email).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                msg = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading ->  {
                        _changeDataState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                isVerify = true,
                                otpResponse = result.data.data
                            )
                        }
                    }
                }
            }
        }
    }
}

data class ChangeDataState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isVerify: Boolean = false,
    val isError : Boolean = false,
    val otpResponse: SendOtpResponse? = null,
    val msg: String? = null
)