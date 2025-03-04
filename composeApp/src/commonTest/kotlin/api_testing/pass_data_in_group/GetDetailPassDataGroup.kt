package api_testing.pass_data_in_group

import api_testing.ApiMocking
import data_sample.GroupDetailsSample
import data_sample.baseErrorResponse
import data_sample.baseResponseSample
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.remoteData.RemotePassDataGroupSources
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassDataGroupByIdResponse
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDetailPassDataGroup {

    private lateinit var groupPassDataRemote: RemotePassDataGroupSources
    private val groupDataDetails = GroupDetailsSample()
    private val apiMockEngine = ApiMocking()

    @BeforeTest
    fun setup() {
        val engine = apiMockEngine.setupEngine(apiMockEngine.setupApiMocking(groupDataDetails.passDataGroupId))
        groupPassDataRemote = RemotePassDataGroupSources(engine)
    }

    @AfterTest
    fun close() {
        apiMockEngine.setupApiMocking(groupDataDetails.passDataGroupId).close()
    }

    @Test
    fun `get details pass data in group api success`() = runTest {
        apiMockEngine.givenSuccess()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupPassDataRemote.getDataPassGroupById(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<PassDataGroupByIdResponse>>(baseResponseSample(groupDataDetails.passDataGroupId)), response)
    }

    @Test
    fun `get details pass data in group api error`() = runTest {
        apiMockEngine.givenFailure(500)
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = groupPassDataRemote.getDataPassGroupById(token,2,1)

        assertEquals(Json.decodeFromString<BaseResponse<PassDataGroupByIdResponse>>(baseErrorResponse(500)), response)
    }
}