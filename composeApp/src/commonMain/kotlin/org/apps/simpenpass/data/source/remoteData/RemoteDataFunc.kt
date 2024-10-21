package org.apps.simpenpass.data.source.remoteData

import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.request.AddMember
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.models.response.UserResponseData
import org.apps.simpenpass.models.response.VerifyOtpResponse
import org.apps.simpenpass.models.user_data.UserData

interface UserDataFunc {
    suspend fun login(data: LoginRequest): BaseResponse<UserResponseData>
    suspend fun register(data: RegisterRequest) : BaseResponse<UserResponseData>
    suspend fun logout(token: String?) : BaseResponse<UserResponseData>
}

interface PassDataFunc {
    suspend fun createUserPass(token: String, formData: PassDataRequest, id: Int): BaseResponse<PassResponseData>
    suspend fun editPassData(token: String, editData: PassDataRequest, passId: Int) : BaseResponse<PassResponseData>
    suspend fun listUserPassData(token: String, id: Int) : BaseResponse<List<PassResponseData>>
    suspend fun getUserPassDataById(token: String, passId: Int) : BaseResponse<PassResponseData>
}

interface GroupPassDataFunc {
    suspend fun createGroup(
        token: String,
        formData: GrupPassData,
        id: Int
    ): BaseResponse<GrupPassData>

    suspend fun updateGroupData(data: RegisterRequest): BaseResponse<GrupPassData>
    suspend fun listJoinedGroupBasedOnUser(
        token: String,
        userId: Int
    ): BaseResponse<List<GrupPassData>>
    suspend fun detailGroupData(
        token: String,
        groupId: Int
    ): BaseResponse<DtlGrupPass>
}

interface MemberGroupDataFunc {
    suspend fun addMemberToGroup(token: String, addData: AddMemberRequest, groupId: Int): BaseResponse<List<AddMember>>
    suspend fun deleteOneMemberFromGroup(token: String, userId: Int) : BaseResponse<GrupPassData>
    suspend fun listUserJoinedInGroup(token: String, groupId: Int) : BaseResponse<List<MemberGroupData>>
    suspend fun findUsersToJoinedGroup(token: String, query: String) : BaseResponse<List<UserData>>
}

interface ResetPassFunc {
    suspend fun sendOtp(email: String) : BaseResponse<SendOtpResponse>
    suspend fun verifyOtp(otp: Int, userId: Int) : BaseResponse<VerifyOtpResponse>
    suspend fun resetPassword(password: String, token: String) : BaseResponse<String>
}