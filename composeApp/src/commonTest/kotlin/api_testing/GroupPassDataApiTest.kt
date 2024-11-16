package api_testing

import data_sample.ListUserJoinedGroupSample
import data_sample.baseReponseSample
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.utils.Constants
import kotlin.test.Test
import kotlin.test.assertEquals

class GroupPassDataApiTest {
    private val listUserJoinedGroupSample = ListUserJoinedGroupSample()
    private val apiMockEngine = ApiMocking()
    private val apiClient = apiMockEngine.setupApiMocking(listUserJoinedGroupSample.data)

    @Test
    fun `get group joined user from api`() = runBlocking {
        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val userId = 1

        val response = apiClient.get(Constants.BASE_API_URL+"groupPass/$userId"){
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        println(response.bodyAsText())

        assertEquals(baseReponseSample(listUserJoinedGroupSample.data), response.bodyAsText())
    }
}