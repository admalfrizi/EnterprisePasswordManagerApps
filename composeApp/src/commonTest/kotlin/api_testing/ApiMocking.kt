package api_testing

import data_sample.baseErrorResponse
import data_sample.baseReponseSample
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json

class ApiMocking{
    private var isSuccess: Boolean? = null
        get() = field ?: throw IllegalStateException("Mock has not beet initialized")

    fun givenSuccess() {
        isSuccess = true
    }

    fun givenFailure() {
        isSuccess = false
    }

    fun setupApiMocking(dataResponse : String) : HttpClient{
        val engine = MockEngine { request ->

            val statusCode = if (isSuccess == true) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.InternalServerError
            }

            val responseContent = if(isSuccess == true){
                baseReponseSample(dataResponse)
            } else {
                baseErrorResponse(HttpStatusCode.InternalServerError.value)
            }


            respond(
                content = ByteReadChannel(responseContent),
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        explicitNulls = true
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}