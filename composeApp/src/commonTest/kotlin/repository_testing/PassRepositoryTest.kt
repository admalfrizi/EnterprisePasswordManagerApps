package repository_testing

import androidx.compose.runtime.mutableStateOf
import api_testing.ApiMocking
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.data.repository.PassRepository
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import org.apps.simpenpass.models.response.DataPassWithAddContent
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
    fun `list user Pass data result message test success`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        val localStoreData = LocalStoreData(get())
        val passRepo = PassRepository(remotePassDataSources,localStoreData)

        val result = mutableStateOf(false)

        apiMockEngine.givenSuccess()

        passRepo.testListUserPassData("8|RbSe0TZ8NP2HkqC5PChKJb2Ze1SLy5SxdnRjqFxL9c09b72a",2).collect { res ->
            when(res){
                is NetworkResult.Success -> {
                    result.value = res.data.success
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }

        assertEquals(true,result.value)
    }

    @Test
    fun `list user pass data result data test success`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        val localStoreData = LocalStoreData(get())
        val passRepo = PassRepository(remotePassDataSources,localStoreData)

        var result = emptyList<DataPassWithAddContent>()

        apiMockEngine.givenSuccess()

        passRepo.testListUserPassData("8|RbSe0TZ8NP2HkqC5PChKJb2Ze1SLy5SxdnRjqFxL9c09b72a",2).collect { res ->
            when(res){
                is NetworkResult.Success -> {
                    result = res.data.data!!
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }

        assertEquals(true,result.isNotEmpty())
    }


    @Test
    fun `list user pass data result message test error`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        val localStoreData = LocalStoreData(get())
        val passRepo = PassRepository(remotePassDataSources,localStoreData)

        val result = mutableStateOf("")

        apiMockEngine.givenFailure()

        passRepo.testListUserPassData("8|RbSe0TZ8NP2HkqC5PChKJb2Ze1SLy5SxdnRjqFxL9c09b72a",2).collect { res ->
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