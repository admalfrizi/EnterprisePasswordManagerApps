package org.apps.simpenpass.data.source.remoteData

import org.apps.simpenpass.models.pass_data.AddContentPassData
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.AddMember
import org.apps.simpenpass.models.request.AddRoleRequest
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.response.AddRoleReponse
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.DataPassWithAddContent
import org.apps.simpenpass.models.response.LatestPassDataResponse
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.models.response.SearchResultResponse
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
    suspend fun latestUserPassData(token: String, id: Int) : BaseResponse<LatestPassDataResponse>
    suspend fun listUserPassData(token: String, id: Int) : BaseResponse<List<DataPassWithAddContent>>
    suspend fun getUserPassDataById(token: String, passId: Int) : BaseResponse<PassResponseData>
    suspend fun addContentDataPass(token: String, passId: Int,addContentPass: List<InsertAddContentDataPass>): BaseResponse<List<AddContentPassData>>
    suspend fun listContentData(token: String, passId: Int): BaseResponse<List<AddContentPassData>>
}

interface GroupPassDataFunc {
    suspend fun createGroup(
        token: String,
        insertData: AddGroupRequest,
        imgName: String,
        imgFile: ByteArray?
    ): BaseResponse<GrupPassData>

    suspend fun updateGroupData(
        token: String,
        groupId: Int,
        data: AddGroupRequest,
        imgName: String,
        imgFile: ByteArray?
    ): BaseResponse<GrupPassData>
    suspend fun listJoinedGroupBasedOnUser(
        token: String,
        userId: Int
    ): BaseResponse<List<GrupPassData>>
    suspend fun detailGroupData(
        token: String,
        groupId: Int
    ): BaseResponse<DtlGrupPass>
    suspend fun searchGroup(
        token: String,
        query: String
    ): BaseResponse<GrupPassData>
}

interface MemberGroupDataFunc {
    suspend fun addMemberToGroup(token: String, addData: List<AddMember>, groupId: Int): BaseResponse<List<AddMember>>
    suspend fun deleteOneMemberFromGroup(token: String, userId: Int) : BaseResponse<GrupPassData>
    suspend fun listUserJoinedInGroup(token: String, groupId: Int) : BaseResponse<List<MemberGroupData>>
    suspend fun findUsersToJoinedGroup(token: String, query: String) : BaseResponse<SearchResultResponse>
}

interface ResetPassFunc {
    suspend fun sendOtp(email: String) : BaseResponse<SendOtpResponse>
    suspend fun verifyOtp(otp: Int, userId: Int) : BaseResponse<VerifyOtpResponse>
    suspend fun resetPassword(password: String, token: String) : BaseResponse<String>
}

interface RolePositionFunc {
    suspend fun addRolePositionInGroup(
        token: String,
        role: AddRoleRequest,
        groupId: Int
    ): BaseResponse<AddRoleReponse>

    suspend fun listRolePositionInGroup(
        token: String,
        groupId: Int
    ): BaseResponse<List<RoleGroupData>>
}