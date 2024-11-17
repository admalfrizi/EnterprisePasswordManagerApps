package org.apps.simpenpass.presentation.ui.group_pass

sealed interface DtlGroupAction {
    data object onUpdateGroupDtl : DtlGroupAction
}