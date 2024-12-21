package api_testing

import data_sample.baseErrorResponse
import data_sample.baseResponseSample
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

    private var isSuccess: Boolean? = false
    private var isEmpty: Boolean? = false
    private var codeError: Int? = null

    fun givenSuccess() {
        isSuccess = true
    }

    fun givenFailure(errorCode: Int) {
        codeError = errorCode
    }

    fun givenEmptyData() {
        isSuccess = true
        isEmpty = true
    }

    fun setupApiMocking(dataResponse : String) : MockEngine {
        val engine = MockEngine { request ->

            val codeError = if(isSuccess == false){
                when(codeError){
                    404 -> HttpStatusCode.NotFound
                    500 -> HttpStatusCode.InternalServerError
                    400 -> HttpStatusCode.BadRequest
                    else -> {
                        HttpStatusCode.fromValue(0)
                    }
                }
            } else if (isSuccess == true){
                HttpStatusCode.OK
            } else {
                HttpStatusCode.fromValue(0)
            }

            val checkData = if(isEmpty == true){
                ByteReadChannel(baseResponseSample("[]"))
            } else {
                ByteReadChannel(baseResponseSample(dataResponse))
            }

            respond(
                content = if(isSuccess == true) checkData else ByteReadChannel(
                    baseErrorResponse(codeError.value)
                ),
                status = codeError,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        return engine
    }

    fun setupEngine(engine : MockEngine) : HttpClient {
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