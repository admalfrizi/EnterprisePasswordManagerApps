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

class GetUserPassDataApiTest {
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
//
//    @Test
//    fun `get user pass data error api`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking("null")
//
//        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
//        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
//            contentType(ContentType.Application.Json)
//            parameter("userId", 2)
//            header(HttpHeaders.Authorization, "Bearer $token")
//        }
//
//        assertEquals(baseErrorResponse(500), response.bodyAsText())
//    }
//
//    @Test
//    fun `get add content user pass data api`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassData)
//
//        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
//        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
//            contentType(ContentType.Application.Json)
//            parameter("userId", 2)
//            header(HttpHeaders.Authorization, "Bearer $token")
//        }
//        val convertToClassData = Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(response.body())
//
//        convertToClassData.data?.forEach {
//            assertEquals(false, it.addContentPass!!.isEmpty())
//        }
//
//    }
//
//    @Test
//    fun `get add content user pass data api empty`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassDataWithEmptyAddContent)
//
//        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
//        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
//            contentType(ContentType.Application.Json)
//            parameter("userId", 2)
//            header(HttpHeaders.Authorization, "Bearer $token")
//        }
//        val convertToClassData = Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(response.body())
//
//        convertToClassData.data?.forEach {
//            assertEquals(true, it.addContentPass!!.isEmpty())
//        }
//    }
}