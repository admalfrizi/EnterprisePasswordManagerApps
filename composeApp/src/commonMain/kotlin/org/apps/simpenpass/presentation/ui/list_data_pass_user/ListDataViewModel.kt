package org.apps.simpenpass.presentation.ui.list_data_pass_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult

class ListDataViewModel(
    private val repo: PassRepository
) : ViewModel() {

    private val _listDataState = MutableStateFlow(ListDataState())
    val listDataState = _listDataState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.listUserPassData().collect { result ->
                when(result) {
                    is NetworkResult.Error -> {
                        _listDataState.update {
                            it.copy(
                                error = result.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _listDataState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _listDataState.update {
                            it.copy(
                                data = result.data!!,
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }

}

data class ListDataState(
    val isLoading : Boolean = false,
    val data : List<PassResponseData> = emptyList(),
    val error : String? = null
)