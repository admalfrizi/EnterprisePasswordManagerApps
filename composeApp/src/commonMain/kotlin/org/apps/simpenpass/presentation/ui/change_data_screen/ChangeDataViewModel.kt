package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.response.SendOtpResponse

class ChangeDataViewModel(
    private val userRepo: UserRepository
): ViewModel() {
    private val _changeDataState = MutableStateFlow(ChangeDataState())
    val changeDataState = _changeDataState.asStateFlow()


}

data class ChangeDataState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isVerify: Boolean = false,
    val isError : Boolean = false,
    val otpResponse: SendOtpResponse? = null,
    val msg: String? = null
)