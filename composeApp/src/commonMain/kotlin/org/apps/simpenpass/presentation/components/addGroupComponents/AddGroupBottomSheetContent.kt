package org.apps.simpenpass.presentation.components.addGroupComponents

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope
import org.apps.simpenpass.presentation.ui.add_group.AddDescSection
import org.apps.simpenpass.presentation.ui.add_group.AddMemberSection

enum class ContentType {
    ADD_DESC,
    ADD_MEMBER,
}

@Composable
fun AddGroupBottomSheetContent(
    contentType: ContentType,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    keyboardController: SoftwareKeyboardController,
    desc: MutableState<String>,
    findMember: MutableState<String>,
    interactionSource: MutableInteractionSource
){
    when(contentType){
        ContentType.ADD_DESC -> AddDescSection(scope,sheetState,keyboardController,desc,interactionSource)
        ContentType.ADD_MEMBER -> AddMemberSection(scope,sheetState, findMember,keyboardController)
    }
}