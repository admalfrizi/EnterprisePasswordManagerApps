package repository_testing

import api_testing.ApiMocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryTest  {

    private val apiMockEngine = ApiMocking()
//    private val apiClient = apiMockEngine.client


    @Test
    fun `get user data logged in `() = runTest {

        val result = ""

        assertEquals(true,result.isNotEmpty())
    }

}