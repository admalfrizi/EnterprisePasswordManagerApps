package api_testing.group_data_pass_api

import api_testing.ApiMocking
import data_sample.GroupDetailsSample
import data_sample.baseErrorResponse
import data_sample.baseResponseSample
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.remoteData.RemotePassDataGroupSources
import org.apps.simpenpass.models.pass_data.PassDataGroup
import org.apps.simpenpass.models.response.BaseResponse
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetListDataPassInGroupApiTest {

    private val groupDetailsSample = GroupDetailsSample()
    private lateinit var passDataGroupRemote : RemotePassDataGroupSources
    private val apiMockEngine = ApiMocking()

    @BeforeTest
    fun setup() {
        val engine = apiMockEngine.setupEngine(apiMockEngine.setupApiMocking(groupDetailsSample.listPassDataGroup))
        passDataGroupRemote = RemotePassDataGroupSources(engine)
    }

    @AfterTest
    fun close() {
        apiMockEngine.setupApiMocking(groupDetailsSample.listPassDataGroup).close()
    }

    @Test
    fun `get list pass data in group api success`() = runTest {
        apiMockEngine.givenSuccess()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = passDataGroupRemote.listGroupPassword(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<List<PassDataGroup>>>(baseResponseSample(groupDetailsSample.listPassDataGroup)), response)
    }

    @Test
    fun `get list pass data in group api error`() = runTest {
        apiMockEngine.givenFailure(500)
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = passDataGroupRemote.listGroupPassword(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<List<PassDataGroup>>>(
            baseErrorResponse(500)
        ), response)
    }

    @Test
    fun `get list pass data in group empty api`() = runTest {
        apiMockEngine.givenEmptyData()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = passDataGroupRemote.listGroupPassword(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<List<PassDataGroup>>>(baseResponseSample("[]")), response)
    }
}