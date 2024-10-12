package org.apps.simpenpass.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult

class HomeViewModel(
    private val userRepo : UserRepository,
    private val passRepo: PassRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        viewModelScope.launch {
            _homeState.update {
                it.copy(
                    name = userRepo.getUserData().name
                )
            }
        }

        viewModelScope.launch {
            passRepo.listUserPassData().collect { result ->
                when(result) {
                    is NetworkResult.Error -> {
                        _homeState.update {
                            it.copy(
                                error = result.error,
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _homeState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is NetworkResult.Success -> {
                        _homeState.update {
                            it.copy(
                                passDataList = result.data.data!!,
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }

}

data class HomeState(
    val name: String? = null,
    val passDataList : List<PassResponseData> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)