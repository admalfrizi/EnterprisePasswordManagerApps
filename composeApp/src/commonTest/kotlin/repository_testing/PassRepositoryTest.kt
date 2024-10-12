package repository_testing

import androidx.compose.runtime.mutableStateOf
import api_testing.ApiMocking
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.response.PassResponseData
import org.apps.simpenpass.utils.NetworkResult
import org.koin.test.KoinTest
import org.koin.test.get
import startApp
import stopApp
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PassRepositoryTest : KoinTest{
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

    @Test
    fun `respository result message test success`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        val localStoreData = LocalStoreData(get())
        val passRepo = PassRepository(remotePassDataSources,localStoreData)

        val result = mutableStateOf("")

        apiMockEngine.givenSuccess()

        passRepo.testListUserPassData("5|nsnj1iiIhMrLJFtEikwoqX1SE2I2Qw9GTmE5TuA954dda6f1",2).collect { res ->
            when(res){
                is NetworkResult.Success -> {
                    result.value = res.data.message
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }

        assertEquals(false,result.value.isEmpty())

    }

    @Test
    fun `respository result data test success`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        val localStoreData = LocalStoreData(get())
        val passRepo = PassRepository(remotePassDataSources,localStoreData)

        var result = emptyList<PassResponseData>()

        apiMockEngine.givenSuccess()

        passRepo.testListUserPassData("5|nsnj1iiIhMrLJFtEikwoqX1SE2I2Qw9GTmE5TuA954dda6f1",2).collect { res ->
            when(res){
                is NetworkResult.Success -> {
                    result = res.data.data!!
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }

        assertEquals(true,result.isNotEmpty())
        assertEquals(6,result.first().id)

//        assertSoftly(firstRepository) {
//            this.id shouldBe 0
//            this.accountName shouldBe ""
//            this.email shouldBe ""
//        }

    }


    @Test
    fun `respository result message test error`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        val localStoreData = LocalStoreData(get())
        val passRepo = PassRepository(remotePassDataSources,localStoreData)

        val result = mutableStateOf("")

        apiMockEngine.givenFailure()

        passRepo.testListUserPassData("5|nsnj1iiIhMrLJFtEikwoqX1SE2I2Qw9GTmE5TuA954dda6f1",2).collect { res ->
            when(res){
                is NetworkResult.Success -> {

                }
                is NetworkResult.Error -> {
                    result.value = res.error
                }
                is NetworkResult.Loading -> {}
            }
        }

        assertEquals("",result.value)

    }
}