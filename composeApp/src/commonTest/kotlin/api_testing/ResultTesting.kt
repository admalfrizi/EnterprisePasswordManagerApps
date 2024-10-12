package api_testing

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.utils.Constants
import kotlin.test.Test
import kotlin.test.assertEquals

class ResultTesting {
    private val apiMockEngine = ApiMocking()
    private val apiClient = apiMockEngine.client

//    @Test
//    fun logoutApiData() = runTest {
//        val token = "4|fq1z3nAYxxD1jD19Jt7qIBBohNqNlqMRBxY2ICKY05e9105e"
//        val response = apiService.client.post(Constants.BASE_API_URL+"logout"){
//            contentType(ContentType.Application.Json)
//            headers {
//                append(HttpHeaders.Accept, "application/json")
//                append(HttpHeaders.Authorization, "Bearer $token")
//            }
//        }
//
//        assertEquals(apiService.client,response.body())
//    }

    @Test
    fun `get user pass data from api`() = runBlocking {
        apiMockEngine.givenSuccess()

        val token = "5|nsnj1iiIhMrLJFtEikwoqX1SE2I2Qw9GTmE5TuA954dda6f1"
        val response = apiClient.get(Constants.BASE_API_URL+"userDataPass"){
            contentType(ContentType.Application.Json)
            parameter("userId", 2)
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(apiMockEngine.jsonContent, response.bodyAsText())
    }
}