import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import org.apps.simpenpass.presentation.components.homeComponents.InfoContainer
import org.apps.simpenpass.presentation.ui.main.home.HomeScreen
import resources.Res
import resources.email_ic
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class UITest {

    @Test
    fun uiHome() = runComposeUiTest {
        setContent {
            InfoContainer("Jumlah Data Password", 10, Color.Blue, Res.drawable.email_ic)
        }

        onNodeWithText("Jumlah Data Password").assertExists()
    }
}