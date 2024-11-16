package repository_testing

import api_testing.ApiMocking
import data_sample.ListUserDataPassSample
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.data.source.remoteData.RemotePassDataSources
import kotlin.test.Test
import kotlin.test.assertEquals

class PassRepositoryMockTest {
    private val apiMockEngine = ApiMocking()
    private val listUserDataPassSample = ListUserDataPassSample()
    private val apiClient = apiMockEngine.setupApiMocking(listUserDataPassSample.listUserPassData)

    @Test
    fun `list user Pass data result message test success`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)

        apiMockEngine.givenSuccess()

        var result = remotePassDataSources.listUserPassData("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",1)

        assertEquals(true,result.success)
    }

    @Test
    fun `list user pass data result data test success`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)
        apiMockEngine.givenSuccess()

        var result = remotePassDataSources.listUserPassData("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",1)

        assertEquals(false,result.data?.isEmpty())
    }


    @Test
    fun `list user pass data result message test error`() = runBlocking {
        val remotePassDataSources = RemotePassDataSources(apiClient)

        apiMockEngine.givenFailure()

        var result = remotePassDataSources.listUserPassData("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",1)

        assertEquals(false,result.success)
    }
}