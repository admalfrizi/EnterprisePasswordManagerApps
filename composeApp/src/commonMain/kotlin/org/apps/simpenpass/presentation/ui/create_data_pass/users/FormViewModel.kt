package org.apps.simpenpass.presentation.ui.create_data_pass.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.models.request.InsertDataRequest
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult

class FormViewModel(
    private val repo: PassRepository
) : ViewModel(){

    private val _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()

    fun createUserPassData(formData: InsertDataRequest){
        viewModelScope.launch {
            repo.createUserPassData(formData).collect { result ->
                when(result) {
                    is NetworkResult.Error -> {
                        _formState.update {
                            it.copy(
                                error = result.error
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _formState.update {
                            it.copy(
                                isLoading = true,
                                isCreated = false,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _formState.update {
                            it.copy(
                                isLoading = false,
                                isCreated = true,
                                msg = result.data.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadDataPassById(passId: Int) {
        viewModelScope.launch {
            repo.getUserPassDataById(passId).collect { result ->
                when(result){
                    is NetworkResult.Error -> {
                        _formState.update {
                            it.copy(
                                error = result.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _formState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _formState.update {
                            it.copy(
                                isLoading = false,
                                passData = result.data.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun editUserPassData(passId: Int, editData: InsertDataRequest ) {
        viewModelScope.launch {
            repo.editUserPassData(editData, passId).collect { result ->
                when(result){
                    is NetworkResult.Error -> {
                        _formState.update {
                            it.copy(
                                error = result.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _formState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _formState.update {
                            it.copy(
                                isLoading = false,
                                isUpdated = true,
                                msg = result.data.message
                            )
                        }
                    }
                }
            }
        }
    }
}

data class FormState(
    val isLoading : Boolean = false,
    val passData: PassResponseData? = null,
    val isCreated: Boolean = false,
    val isUpdated: Boolean = false,
    val error : String? = null,
    val msg : String? = null
)