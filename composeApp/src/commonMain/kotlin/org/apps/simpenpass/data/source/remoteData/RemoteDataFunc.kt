package org.apps.simpenpass.data.source.remoteData

import org.apps.simpenpass.models.pass_data.AddContentPassData
import org.apps.simpenpass.models.pass_data.AddContentPassDataGroup
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.pass_data.MemberGroupData
import org.apps.simpenpass.models.pass_data.PassDataGroup
import org.apps.simpenpass.models.pass_data.RoleGroupData
import org.apps.simpenpass.models.request.AddGroupRequest
import org.apps.simpenpass.models.request.AddMemberRequest
import org.apps.simpenpass.models.request.AddRoleRequest
import org.apps.simpenpass.models.request.InsertAddContentDataPass
import org.apps.simpenpass.models.request.LoginRequest
import org.apps.simpenpass.models.request.PassDataGroupRequest
import org.apps.simpenpass.models.request.PassDataRequest
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.request.UpdateRoleMemberGroupRequest
import org.apps.simpenpass.models.response.AddRoleReponse
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.DataPassWithAddContent
import org.apps.simpenpass.models.response.DetailRoleGroupResponse
import org.apps.simpenpass.models.response.LatestPassDataResponse
import org.apps.simpenpass.models.response.PassDataGroupByIdResponse
import org.apps.simpenpass.models.response.PassGroupResponseData
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.models.response.SearchResultResponse
import org.apps.simpenpass.models.response.SendOtpResponse
import org.apps.simpenpass.models.response.UpdateRoleMemberResponse
import org.apps.simpenpass.models.response.UserResponseData
import org.apps.simpenpass.models.response.VerifyOtpResponse
import org.apps.simpenpass.models.user_data.UserDataStats

interface UserDataFunc {
    suspend fun login(data: LoginRequest): BaseResponse<UserResponseData>
    suspend fun register(data: RegisterRequest) : BaseResponse<UserResponseData>
    suspend fun logout(token: String?) : BaseResponse<UserResponseData>
    suspend fun userDataStats(token: String, userId: Int): BaseResponse<UserDataStats>
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
    suspend fun addMemberToGroup(token: String, addData: List<AddMemberRequest>, groupId: Int): BaseResponse<List<AddMemberRequest>>
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

    suspend fun updateRoleMemberGroup(
        token: String,
        groupId: Int,
        updateRoleMember: UpdateRoleMemberGroupRequest
    ): BaseResponse<UpdateRoleMemberResponse>

    suspend fun detailRoleData(
        token: String,
        roleId: Int
    ): BaseResponse<DetailRoleGroupResponse>

    suspend fun deleteRolePosition(
        token: String,
        groupId: Int,
        roleId: Int
    ): BaseResponse<AddRoleReponse>
}

interface PassDataGroupFunc {
    suspend fun listGroupPassword(token: String,groupId: Int) : BaseResponse<List<PassDataGroup>>
    suspend fun listGroupPasswordRoleFiltered(token: String, groupId: Int, roleId: Int) : BaseResponse<List<PassDataGroup>>
    suspend fun getDataPassGroupById(token: String, groupId: Int, passDataGroupId: Int) : BaseResponse<PassDataGroupByIdResponse>
    suspend fun addPassGroup(token: String,groupId: Int,addDataPass: PassDataGroupRequest) : BaseResponse<PassGroupResponseData>
    suspend fun updatePassGroup(token: String, groupId: Int,passDataGroupId: Int, updatePassData: PassDataGroupRequest ) : BaseResponse<PassGroupResponseData>
    suspend fun addContentPassData(token: String,groupId: Int, passGroupDataId: Int,addContentPassData: List<InsertAddContentDataPass>) : BaseResponse<List<AddContentPassDataGroup>>
}