package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.ForgotPassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.utils.NetworkResult

class ChangeDataViewModel(
    private val userRepo: UserRepository,
    private val forgotPassRepo: ForgotPassRepository
): ViewModel() {
    private val _changeDataState = MutableStateFlow(ChangeDataState())
    val changeDataState = _changeDataState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepo.getUserData().let { userData ->
                _changeDataState.update {
                    it.copy(
                        userId = userData.id
                    )
                }
            }
        }
    }

    fun verifyOtp(otp: String){
        viewModelScope.launch {
            forgotPassRepo.verifyOtp(otp.toInt(), _changeDataState.value.userId.toString()).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
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
                                resetPassTokens = result.data.data?.tokenOtp,
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
    val userId: Int? = null,
    val otpResponse: SendOtpResponse? = null,
    val resetPassTokens: String? = null,
    val msg: String? = null
)