package api_testing

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class ApiMocking {

    private val listUserPassData = """[
        {
            "id": 6,
            "user_id": 2,
            "jenis_data": "Streaming Video",
            "account_name": "Prime Video",
            "username": "adams26",
            "password": "dwdwqdfewf",
            "email": "sadam89@gmail.com",
            "url": "prime.com",
            "desc": "dwadigqwdiq",
            "created_at": "2024-10-11T12:29:36.000000Z",
            "updated_at": "2024-10-11T12:29:36.000000Z"
        },
        {
            "id": 8,
            "user_id": 2,
            "jenis_data": null,
            "account_name": "Netflix",
            "username": "dwadawd",
            "password": "yr5ye5ydwar3qr",
            "email": "fwefowehfwe",
            "url": "fsefnseregerge",
            "desc": "y54y443t34y",
            "created_at": "2024-10-11T12:43:01.000000Z",
            "updated_at": "2024-10-11T12:43:01.000000Z"
        },
        {
            "id": 9,
            "user_id": 2,
            "jenis_data": "gergerigheir",
            "account_name": "uigfsefuisef",
            "username": "342r84gwe8i",
            "password": "eff234r23",
            "email": "dwdgi1gd",
            "url": "nytjmytj",
            "desc": "12esqw",
            "created_at": "2024-10-11T12:44:33.000000Z",
            "updated_at": "2024-10-11T12:44:33.000000Z"
        }
    ]
    """.trimIndent()

    val jsonContent = """{
        "success": true,
        "code": 200,
        "message": "Berikut Data untuk Pengguna",
        "data": $listUserPassData
    }
    """.trimIndent()


    private var isSuccess: Boolean? = null
        get() = field ?: throw IllegalStateException("Mock has not beet initialized")

    fun givenSuccess() {
        isSuccess = true
    }

    fun givenFailure() {
        isSuccess = false
    }

    val engine = MockEngine { request ->

        val statusCode = if (isSuccess == true) {
            HttpStatusCode.OK
        } else {
            HttpStatusCode.InternalServerError
        }

        val searchKeyword = request.url.parameters["userId"] ?: ""
        val responseContent = if(searchKeyword == "2"){
            jsonContent
        } else {
            "[]"
        }

        respond(
            content = responseContent,
            status = statusCode,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(engine) {
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