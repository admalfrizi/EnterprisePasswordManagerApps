package org.apps.simpenpass.screen

sealed class Screen(val route : String) {
    data object Root : Screen(route = "root")
    data object Auth : Screen(route = "auth")

    data object Login : Screen(route = "login")
    data object Register : Screen(route = "register")
    data object SendOtp : Screen(route = "sendOtp")
    data object RecoveryPass : Screen(route = "recovery_pass/{token}"){
        fun token(token : String) = "recovery_pass/$token"

        const val ARG_TOKEN = "token"
    }
    data object VerifyOtp : Screen(route = "verify_otp/{userId}"){
        fun userId(userId : String) = "verify_otp/$userId"

        const val ARG_USER_ID = "userId"
    }

    data object Main : Screen(route = "main")
    data object Home : Screen(route = "home")
    data object Profile : Screen(route = "profile")
    data object Group : Screen(route = "group")

    data object GroupPass : Screen(route = "groupPass/dtl/{groupId}"){
        fun groupId(groupId : String) = "groupPass/dtl/$groupId"

        const val ARG_GROUP_ID = "groupId"
    }

    data object FormPassData: Screen(route = "formPassData/{passId}"){
        fun passDataId(passId : String) = "formPassData/$passId"

        const val ARG_PASS_ID = "passId"
    }
    data object ListPassDataUser : Screen(route = "listPassData")
    data object GroupPassDtl : Screen(route = "groupDetail")

    data object EditAnggota : Screen(route = "groupPass/dtl/editAnggota/{groupId}"){
        fun groupId(groupId : String) = "groupPass/dtl/editAnggota/$groupId"

        const val ARG_GROUP_ID = "groupId"
    }

    data object RetrieveDataPass : Screen(route = "groupPass/dtl/retrieveData")
    data object AddGroupPass : Screen(route = "addGroup")
    data object EditRole : Screen(route = "editRole")
}