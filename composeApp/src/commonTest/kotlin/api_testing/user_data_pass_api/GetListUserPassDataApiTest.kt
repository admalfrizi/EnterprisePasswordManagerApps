package api_testing.user_data_pass_api

import api_testing.ApiMocking
import data_sample.ListUserDataPassSample
import data_sample.baseErrorResponse
import data_sample.baseResponseSample
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.DataPassWithAddContent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetListUserPassDataApiTest {
    private lateinit var userPassDataRemote: RemotePassDataSources
    private val listUserDataPassSample = ListUserDataPassSample()
    private val apiMockEngine = ApiMocking()

    @BeforeTest
    fun setup() {
        val engine = apiMockEngine.setupEngine(apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassData))
        userPassDataRemote = RemotePassDataSources(engine)
    }

    @AfterTest
    fun close() {
        apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassData).close()
    }

    @Test
    fun `get user pass data api success`() = runTest {
        apiMockEngine.givenSuccess()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = userPassDataRemote.listUserPassData(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(baseResponseSample(listUserDataPassSample.listUserPassData)), response)
    }

    @Test
    fun `get user pass data api error`() = runTest {
        apiMockEngine.givenFailure(500)
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = userPassDataRemote.listUserPassData(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(
            baseErrorResponse(500)
        ), response)
    }

    @Test
    fun `get user pass data empty api`() = runTest {
        apiMockEngine.givenEmptyData()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = userPassDataRemote.listUserPassData(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(baseResponseSample("[]")), response)
    }
}