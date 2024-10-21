package org.apps.simpenpass.data.source.remoteData

import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.models.response.UserResponseData
import org.apps.simpenpass.models.response.VerifyOtpResponse

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
        formData: PassDataRequest,
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
    suspend fun addMemberToGroup(token: String, addData: AddMemberRequest, id: Int): BaseResponse<GrupPassData>
    suspend fun deleteOneMemberFromGroup(token: String, userId: Int) : BaseResponse<GrupPassData>
    suspend fun listUserJoinedInGroup(token: String, groupId: Int) : BaseResponse<List<MemberGroupData>>
}

interface ResetPassFunc {
    suspend fun sendOtp(email: String) : BaseResponse<SendOtpResponse>
    suspend fun verifyOtp(otp: Int, userId: Int) : BaseResponse<VerifyOtpResponse>
    suspend fun resetPassword(password: String, token: String) : BaseResponse<String>
}