package api_testing.group_data_pass_api

import api_testing.ApiMocking
import data_sample.ListUserJoinedGroupSample
import data_sample.baseErrorResponse
import data_sample.baseResponseSample
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import org.apps.simpenpass.models.pass_data.GrupPassData
import org.apps.simpenpass.models.response.BaseResponse
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetListGroupPassDataApiTest {
    private val listUserJoinedGroupSample = ListUserJoinedGroupSample()
    private lateinit var groupDataRemote : RemoteGroupDataSources
    private val apiMockEngine = ApiMocking()

    @BeforeTest
    fun setup() {
        val engine = apiMockEngine.setupEngine(apiMockEngine.setupApiMocking(listUserJoinedGroupSample.data))
        groupDataRemote = RemoteGroupDataSources(engine)
    }

    @AfterTest
    fun close() {
        apiMockEngine.setupApiMocking(listUserJoinedGroupSample.data).close()
    }

    @Test
    fun `get list group pass data api success`() = runTest {
        apiMockEngine.givenSuccess()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupDataRemote.listJoinedGroupBasedOnUser(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<List<GrupPassData>>>(baseResponseSample(listUserJoinedGroupSample.data)), response)
    }

    @Test
    fun `get list group pass data api error`() = runTest {
        apiMockEngine.givenFailure(500)
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupDataRemote.listJoinedGroupBasedOnUser(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<List<GrupPassData>>>(
            baseErrorResponse(500)
        ), response)
    }

    @Test
    fun `get list group pass data empty api`() = runTest {
        apiMockEngine.givenEmptyData()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupDataRemote.listJoinedGroupBasedOnUser(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<List<GrupPassData>>>(baseResponseSample("[]")), response)
    }
}