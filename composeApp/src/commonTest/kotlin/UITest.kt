import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.presentation.components.homeComponents.InfoContainer
import org.apps.simpenpass.presentation.ui.main.home.HomeScreen
import resources.Res
import resources.email_ic
import kotlin.test.Test

//@OptIn(ExperimentalTestApi::class)
//class UITest {

//    @Test
//    fun uiHome() = runComposeUiTest {
//        setContent {
//            InfoContainer("Jumlah Data Password", 10, Color.Blue, Res.drawable.email_ic)
//        }
//
//        onNodeWithText("Jumlah Data Password").assertExists()
//    }

//    @Test
//    fun sampleClientTest() {
//        runBlocking {
//            val mockEngine = MockEngine { request ->
//                respond(
//                    content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
//                    status = HttpStatusCode.OK,
//                    headers = headersOf(HttpHeaders.ContentType, "application/json")
//                )
//            }
//            val apiClient = ApiClient(mockEngine)
//
//            Assert.assertEquals("127.0.0.1", apiClient.getIp().ip)
//        }
//    }
//}