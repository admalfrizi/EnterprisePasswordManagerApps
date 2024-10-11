package org.apps.simpenpass.presentation.ui.main.group

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.presentation.ui.main.home.HomeState

class GroupViewModel(

): ViewModel() {
    private val _groupState = MutableStateFlow(GroupState())
    val groupState = _groupState.asStateFlow()
}

data class GroupState(
    val groupData: List<GrupPassData?> = emptyList(),
)