package org.apps.simpenpass.utils

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class ModalBottomSheetDataValue<T: Any>(
    val modalBottomSheetState: ModalBottomSheetState
) {
    var data: MutableState<T?> = mutableStateOf(null)

    suspend fun openModal(data: T) {
        this.data.value = data
        if (!modalBottomSheetState.isVisible)
            modalBottomSheetState.show()
    }

    suspend fun closeModal() {
        this.data.value = null
        if (modalBottomSheetState.isVisible)
            modalBottomSheetState.hide()
    }
}