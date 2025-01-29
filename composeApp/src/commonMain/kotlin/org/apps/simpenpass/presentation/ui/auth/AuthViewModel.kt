package org.apps.simpenpass.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.ForgotPassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.request.SendUserDataPassToDecrypt
import org.apps.simpenpass.models.response.GetPassDataEncrypted
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.utils.NetworkResult

class AuthViewModel(
    private val userRepo : UserRepository,
    private val forgotPassRepo: ForgotPassRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepo.getToken().collectLatest { token ->
                _authState.update {
                    it.copy(
                        token = token
                    )
                }
            }
        }

        viewModelScope.launch {
            userRepo.getStatusLoggedIn().collectLatest { status ->
                _authState.update {
                    it.copy(
                        isLoggedIn = status
                    )
                }
            }
        }
    }

    fun login(data: LoginRequest){
        viewModelScope.launch {
            userRepo.login(data).collect { result ->
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
                                userData = result.data,
                                error = ""
                            )
                        }
                    }
                }
            }
        }
    }

    fun register(data: RegisterRequest){
        viewModelScope.launch {
            userRepo.register(data).collect { result ->
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

    fun sendOtp(email : String){
        viewModelScope.launch {
            forgotPassRepo.sendOtp(email).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading ->  {
                        _authState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isSendOtp = true,
                                otpResponse = result.data.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun verifyOtp(otp: String,userId: String){
        viewModelScope.launch {
            forgotPassRepo.verifyOtp(otp.toInt(),true,userId).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading ->  {
                        _authState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isVerify = true,
                                resetPassTokens = result.data.data?.tokenOtp,
                                error = ""
                            )
                        }
                    }
                }
            }
        }
    }

    fun verifyPassForDecrypt(
        userId: Int,
        password: String
    ) {
        viewModelScope.launch {
            forgotPassRepo.verifyOldPassword(userId,password).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                message = res.error,
                                key = ""
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
                                isVerify = res.data.data!!,
                                key = if(res.data.data) password else ""
                            )
                        }
                    }
                }
            }
        }

    }

    fun getUserPassDataEncrypted(userId: Int){
        viewModelScope.launch {
            forgotPassRepo.getUserDataPassEncrypted(userId).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                message = res.error
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
                                userPassData = res.data.data?.listPassData
                            )
                        }
                    }
                }
            }
        }
    }

    fun sendUserDataPassToDecrypt(sendDataPassToDecrypt: SendUserDataPassToDecrypt){
        viewModelScope.launch {
            forgotPassRepo.updateUserDataPassWithNewKey(sendDataPassToDecrypt).flowOn(Dispatchers.IO).collect { res ->
                when(res) {
                    is NetworkResult.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                message = res.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _authState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isDecrypt = true,
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
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                message = result.data.message,
                                isResetPass = true
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
    val isSendOtp: Boolean = false,
    var isVerify: Boolean = false,
    var isDecrypt: Boolean = false,
    val isResetPass: Boolean = false,
    val userData: UserData? = null,
    val otpResponse: SendOtpResponse? = null,
    val userPassData: List<GetPassDataEncrypted>? = emptyList(),
    val token: String? = null,
    val resetPassTokens: String? = null,
    var error: String? = null,
    var key: String? = null,
    val message: String? = null
)