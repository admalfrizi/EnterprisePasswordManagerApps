package org.apps.simpenpass.screen

sealed class Screen(val route : String) {
    data object NavScreen : Screen(route = "navScreen")
    data object Auth : Screen(route = "auth")
    data object GroupPass : Screen(route = "groupPass")

    data object Login : Screen(route = "login")
    data object Register : Screen(route = "register")
    data object RecoveryPass : Screen(route = "recovery_pass")

    data object Main : Screen(route = "main")
    data object Home : Screen(route = "home")
    data object Profile : Screen(route = "profile")
    data object Group : Screen(route = "group")

    data object PassData: Screen(route = "formPassData")
    data object ListPassDataUser : Screen(route = "listPassData")
    data object GroupPassDtl : Screen(route = "groupDetail")

    data object EditAnggota : Screen(route = "editAnggota")
    data object RetrieveDataPass : Screen(route = "retrieveData")
    data object AddGroupPass : Screen(route = "addGroup")
    data object EditRole : Screen(route = "editRole")
}