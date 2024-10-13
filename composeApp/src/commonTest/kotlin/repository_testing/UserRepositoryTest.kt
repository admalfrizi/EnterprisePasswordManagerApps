package repository_testing

import api_testing.ApiMocking
import org.koin.test.KoinTest
import startApp
import stopApp
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class UserRepositoryTest : KoinTest {

    private val apiMockEngine = ApiMocking()
    private val apiClient = apiMockEngine.client

    @BeforeTest
    fun startKoin() {
        startApp()
    }

    @AfterTest
    fun stopKoin() {
        stopApp()
    }


}