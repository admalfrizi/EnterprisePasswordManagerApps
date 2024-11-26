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
    data object EditRole : Screen(route = "groupPass/dtl/editRole/{groupId}"){
        fun groupId(groupId : String) = "groupPass/dtl/editRole/$groupId"

        const val ARG_GROUP_ID = "groupId"
    }

    data object FormPassGroup: Screen(route = "groupPass/dtl/formGroupPass/{passDataGroupId}/{groupId}"){
        fun passData(
            passDataGroupId : String? = "-1",
            groupId: String
        ) = "groupPass/dtl/formGroupPass/$passDataGroupId/$groupId"

        const val ARG_PASS_DATA_GROUP_ID = "passDataGroupId"
        const val ARG_GROUP_ID = "groupId"
    }

    data object PassDataGroupDtl: Screen(route = "groupPass/dtl/passDataGroup/{groupId}/{passDataGroupId}"){
        fun passDataGroupId(
            passDataGroupId : String,
            groupId: String
        ) = "groupPass/dtl/passDataGroup/$groupId/$passDataGroupId"

        const val ARG_PASS_DATA_GROUP_ID = "passDataGroupId"
        const val ARG_GROUP_ID = "groupId"
    }

    data object GroupSettings: Screen(route = "groupPass/dtl/settings/{groupId}"){
        fun groupId(groupId : String) = "groupPass/dtl/settings/$groupId"

        const val ARG_GROUP_ID = "groupId"
    }

    data object Otp : Screen(route = "otpVerify/{dataType}"){
        fun dataType(
            dataType : String
        ) = "otpVerify/$dataType"

        const val ARG_DATA_TYPE = "dataType"
    }
    data object ChangePass : Screen(route = "changePass/{token}"){
        fun token(
            token : String
        ) = "changePass/$token"

        const val ARG_TOKEN = "token"
    }
}