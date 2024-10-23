package org.apps.simpenpass.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.GroupRepository
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult

class HomeViewModel(
    private val userRepo : UserRepository,
    private val passRepo: PassRepository,
    private val groupRepo: GroupRepository,
    private val konnection: Konnection,
) : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

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
            groupRepo.listJoinedGrup().collect { result ->
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
                                totalGroupJoined = result.data.data?.size,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }

        observeConnection()
    }

    private fun observeConnection() {
        viewModelScope.launch {
            konnection.observeHasConnection().collect { hasConnect ->
                _isConnected.value = hasConnect
                _homeState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getData() {
        viewModelScope.launch {
            if(isConnected.value){
                passRepo.listUserPassData().flowOn(Dispatchers.IO).collectLatest { result ->
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

    override fun onCleared() {
        super.onCleared()
        _homeState.update {
            it.copy(
                passDataList = emptyList(),
                totalGroupJoined = null,
                error = null,
                isLoading = false
            )
        }
    }
}

data class HomeState(
    val name: String? = null,
    val passDataList : List<PassResponseData> = emptyList(),
    val totalGroupJoined: Int? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)