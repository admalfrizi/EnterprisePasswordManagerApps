package api_testing

import data_sample.GroupDetailsSample
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
    private val groupDetailsSample = GroupDetailsSample()
    private val apiMockEngine = ApiMocking()


    @Test
    fun `get group joined user from api`() = runBlocking {
        val apiClient = apiMockEngine.setupApiMocking(listUserJoinedGroupSample.data)
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

    @Test
    fun `get group details from api`() = runBlocking {
        val apiClient = apiMockEngine.setupApiMocking(groupDetailsSample.detailGroupData)
        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val groupId = 2

        val response = apiClient.get(Constants.BASE_API_URL+"dtlGroup/$groupId"){
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        println(response.bodyAsText())

        assertEquals(baseReponseSample(groupDetailsSample.detailGroupData), response.bodyAsText())
    }

    @Test
    fun `get pass data group from api`() = runBlocking {
        val apiClient = apiMockEngine.setupApiMocking(groupDetailsSample.listPassDataGroup)
        apiMockEngine.givenSuccess()

        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
        val groupId = 2

        val response = apiClient.get(Constants.BASE_API_URL+"allPassGroupData/$groupId"){
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        println(response.bodyAsText())

        assertEquals(baseReponseSample(groupDetailsSample.listPassDataGroup), response.bodyAsText())
    }
}