package api_testing

import data_sample.ListUserDataPassSample
import data_sample.baseErrorResponse
import data_sample.baseReponseSample
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.apps.simpenpass.models.response.BaseResponse
import org.apps.simpenpass.models.response.DataPassWithAddContent
import org.apps.simpenpass.utils.Constants
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDataPassApiTesting {
    private val listUserDataPassSample = ListUserDataPassSample()
    private val apiMockEngine = ApiMocking()

    @Test
    fun `get user pass data api`() = runTest {
        val apiClient = apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassData)

        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
            contentType(ContentType.Application.Json)
            parameter("userId", 2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(baseReponseSample(listUserDataPassSample.listUserPassData), response.bodyAsText())
    }

    @Test
    fun `get user pass data empty api`() = runTest {
        val apiClient = apiMockEngine.setupApiMocking("[]")
        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
            contentType(ContentType.Application.Json)
            parameter("userId", 2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(baseReponseSample("[]"), response.bodyAsText())
    }

    @Test
    fun `get user pass data error api`() = runTest {
        val apiClient = apiMockEngine.setupApiMocking("null")
        apiMockEngine.givenFailure()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
            contentType(ContentType.Application.Json)
            parameter("userId", 2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(baseErrorResponse(500), response.bodyAsText())
    }

    @Test
    fun `get add content user pass data api`() = runTest {
        val apiClient = apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassData)
        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
            contentType(ContentType.Application.Json)
            parameter("userId", 2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        val convertToClassData = Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(response.body())

        convertToClassData.data?.forEach {
            assertEquals(false, it.addContentPass!!.isEmpty())
        }

    }

    @Test
    fun `get add content user pass data api empty`() = runTest {
        val apiClient = apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassDataWithEmptyAddContent)
        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
            contentType(ContentType.Application.Json)
            parameter("userId", 2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        val convertToClassData = Json.decodeFromString<BaseResponse<List<DataPassWithAddContent>>>(response.body())

        convertToClassData.data?.forEach {
            assertEquals(true, it.addContentPass!!.isEmpty())
        }
    }
}