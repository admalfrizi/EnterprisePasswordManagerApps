package api_testing.user_data_pass_api

import api_testing.ApiMocking
import data_sample.UserPassDataDetailsSample
import data_sample.baseErrorResponse
import data_sample.baseResponseSample
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.PassResponseData
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDetailsUserPassDataApiTest {

    private lateinit var userPassDataRemote: RemotePassDataSources
    private val detailUserDataPassSample = UserPassDataDetailsSample()
    private val apiMockEngine = ApiMocking()

    @BeforeTest
    fun setup() {
        val engine = apiMockEngine.setupEngine(apiMockEngine.setupApiMocking(detailUserDataPassSample.detailsUserPassData))
        userPassDataRemote = RemotePassDataSources(engine)
    }

    @AfterTest
    fun close() {
        apiMockEngine.setupApiMocking(detailUserDataPassSample.detailsUserPassData).close()
    }

    @Test
    fun `get details user pass data api success`() = runTest {
        apiMockEngine.givenSuccess()
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = userPassDataRemote.getUserPassDataById(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<PassResponseData>>(baseResponseSample(detailUserDataPassSample.detailsUserPassData)), response)
    }

    @Test
    fun `get details user pass data api error`() = runTest {
        apiMockEngine.givenFailure(500)
        val token = "1|TOvHBYVHgMJSHPInRQh7SYbdJdN20bLMYmejjPS7741a48c6"
        val response = userPassDataRemote.getUserPassDataById(token,2)

        assertEquals(Json.decodeFromString<BaseResponse<PassResponseData>>(
            baseErrorResponse(500)
        ), response)
    }
}