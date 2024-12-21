package org.apps.simpenpass.presentation.ui.change_data_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.ForgotPassRepository
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.request.UpdateUserDataRequest
import org.apps.simpenpass.models.response.GetPassDataEncrypted
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.models.user_data.LocalUserStore
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.utils.NetworkResult

class ChangeDataViewModel(
    private val userRepo: UserRepository,
    private val forgotPassRepo: ForgotPassRepository,
    private val passRepo: PassRepository
): ViewModel() {
    private val _changeDataState = MutableStateFlow(ChangeDataState())
    val changeDataState = _changeDataState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepo.getUserData().let { userData ->
                _changeDataState.update {
                    it.copy(
                        userId = userData.id,
                    )
                }
            }
        }
    }

    fun getUserData(){
        viewModelScope.launch {
            userRepo.getUserData().let { userData ->
                _changeDataState.update {
                    it.copy(
                        updateData = userData,
                    )
                }
            }
        }
    }

    fun verifyOtp(otp: String, isResetPass: Boolean){
        viewModelScope.launch {
            forgotPassRepo.verifyOtp(otp.toInt(), isResetPass,_changeDataState.value.userId.toString()).collect { result ->
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
                                resetPassTokens = result.data.data?.tokenOtp ?: "",
                            )
                        }
                    }
                }
            }
        }
    }

    fun resetPassword(password: String, token: String){
        viewModelScope.launch {
            forgotPassRepo.resetPassword(password, token).collect { result ->
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

                    is NetworkResult.Loading -> {
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
                                msg = result.data.message,
                                isSuccess = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateDataUser(
        userId: Int,
        updateUser: UpdateUserDataRequest
    ){
        viewModelScope.launch {
            userRepo.updateDataUser(userId,updateUser).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                                userData = res.data.data
                            )
                        }
                    }

                }
            }
        }
    }

    fun getUserPassDataEncrypted(){
        viewModelScope.launch {
            passRepo.getUserDataPassEncrypted().flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                msg = res.error
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        _changeDataState.update {
                            it.copy(
                                isLoading = false,
                                userPassData = res.data.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveUserData(user: UserData){
        viewModelScope.launch {
            userRepo.saveUserData(user)
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
    val updateData: LocalUserStore? = null,
    val userData: UserData? = null,
    val userPassData: List<GetPassDataEncrypted>? = emptyList(),
    val resetPassTokens: String? = null,
    val msg: String? = null
)