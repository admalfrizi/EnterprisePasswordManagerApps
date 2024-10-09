package org.apps.simpenpass.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.repository.UserRepository

class HomeViewModel(
    private val repo : UserRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        viewModelScope.launch {
            _homeState.update {
                it.copy(
                    name = repo.getUserData().name
                )
            }
        }
    }

}

data class HomeState(
    val name: String? = null
)