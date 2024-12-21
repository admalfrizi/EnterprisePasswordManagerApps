package api_testing.group_data_pass_api

import api_testing.ApiMocking
import data_sample.GroupDetailsSample
import data_sample.baseErrorResponse
import data_sample.baseResponseSample
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import org.apps.simpenpass.models.pass_data.DtlGrupPass
import org.apps.simpenpass.models.response.BaseResponse
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetGroupDetailApiTest {
    private val groupDetailsSample = GroupDetailsSample()
    private lateinit var groupDataRemote : RemoteGroupDataSources
    private val apiMockEngine = ApiMocking()

    @BeforeTest
    fun setup() {
        val engine = apiMockEngine.setupEngine(apiMockEngine.setupApiMocking(groupDetailsSample.detailGroupData))
        groupDataRemote = RemoteGroupDataSources(engine)
    }

    @AfterTest
    fun close() {
        apiMockEngine.setupApiMocking(groupDetailsSample.detailGroupData).close()
    }

    @Test
    fun `get group detail from api`() = runTest {
        apiMockEngine.givenSuccess()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupDataRemote.detailGroupData(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<DtlGrupPass>>(baseResponseSample(groupDetailsSample.detailGroupData)), response)
    }

    @Test
    fun `get group detail from api error`() = runTest {
        apiMockEngine.givenFailure(500)
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupDataRemote.detailGroupData(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<DtlGrupPass>>(baseErrorResponse(500)), response)
    }

}