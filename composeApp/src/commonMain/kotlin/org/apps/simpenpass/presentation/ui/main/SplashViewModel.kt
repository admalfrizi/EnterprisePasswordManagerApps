package org.apps.simpenpass.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.screen.Screen

class SplashViewModel(
    localStoreData: LocalStoreData
) : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _currentScreen = MutableStateFlow<Screen?>(null)
    val currentScreen = _currentScreen.asStateFlow()

    init {
        viewModelScope.launch {
            localStoreData.checkLoggedIn().flowOn(Dispatchers.Default).collect { isLoggedIn ->
                if(isLoggedIn){
                    _isLoading.value = false
                    _currentScreen.value = Screen.Root
                } else {
                    _isLoading.value = false
                    _currentScreen.value = Screen.Auth
                }
            }
        }
    }
}