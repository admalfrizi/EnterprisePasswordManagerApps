package org.apps.simpenpass.presentation.ui.create_data_pass.users

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
import kotlinx.coroutines.withContext
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.pass_data.AddContentPassData
import org.apps.simpenpass.models.request.FormAddContentPassData
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult

class FormViewModel(
    private val repo: PassRepository,
    private val userRepo: UserRepository
) : ViewModel(){

    private val _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()

    fun createUserPassData(
        formData: PassDataRequest,
        insertAddContentPassData: MutableList<InsertAddContentDataPass>
    ){
        viewModelScope.launch {
            repo.createUserPassData(formData,insertAddContentPassData).flowOn(Dispatchers.IO).collect { result ->
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
                                msg = result.data.message,
                                passData = result.data.data
                            )
                        }

//                        if(formState.value.isCreated){
//                            withContext(Dispatchers.IO){
//                                addContentDataToDb(
//                                    formState.value.passData!!.id!!,
//                                    formState.value.insertAddContentPassData
//                                )
//                            }
//                        }
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

                        if(formState.value.passData?.id != null){
                            withContext(Dispatchers.IO){
                                listContentPassData(passId)
                            }
                        }
                    }
                }
            }
        }
    }

    fun editUserPassData(
        passId: Int,
        editData: PassDataRequest,
        insertAddContentPassData: MutableList<InsertAddContentDataPass>,
        deleteAddContentPassData: MutableList<FormAddContentPassData>,
        updateAddContentPassData: MutableList<FormAddContentPassData>
    ) {
        viewModelScope.launch {
            repo.editUserPassData(editData, passId,insertAddContentPassData,deleteAddContentPassData,updateAddContentPassData).flowOn(Dispatchers.IO).collect { result ->
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

//                        withContext(Dispatchers.IO){
//                            addContentDataToDb(
//                                passId,
//                                formState.value.insertAddContentPassData
//                            )
//                        }
                    }
                }
            }
        }
    }

    private fun listContentPassData(
        passId: Int
    ){
        viewModelScope.launch {
            repo.listContentData(passId).collectLatest {  result ->
                when(result){
                    is NetworkResult.Error -> {
                        _formState.update {
                            it.copy(
                                isLoading = false,
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
                                listAddContentPassData = result.data.data!!,
                            )
                        }
                    }
                }
            }
        }
    }

    fun verifyPassForDecrypt(
        password: String
    ) {
        viewModelScope.launch {
            userRepo.verifyPassForDecrypt(password).flowOn(Dispatchers.IO).collect { res ->
                when(res){
                    is NetworkResult.Error -> {
                        _formState.update {
                            it.copy(
                                isLoading = false,
                                error = res.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _formState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _formState.update {
                            it.copy(
                                isLoading = false,
                                isPassVerify = true,
                                key = password
                            )
                        }
                    }
                }
            }
        }

    }

    fun resetValue() {
        _formState.value = FormState()
    }
}

data class FormState(
    val isLoading : Boolean = false,
    val passData: PassResponseData? = null,
    val listAddContentPassData: List<AddContentPassData> = emptyList(),
    val isCreated: Boolean = false,
    val isUpdated: Boolean = false,
    var isPassVerify: Boolean = false,
    val key: String? = null,
    val error : String? = null,
    val msg : String? = null,
    val msgAddContentData: String? = null
)