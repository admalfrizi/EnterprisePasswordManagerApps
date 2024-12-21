package repository_testing

import api_testing.ApiMocking
import data_sample.GroupDetailsSample
import data_sample.ListUserJoinedGroupSample
import kotlinx.coroutines.test.runTest
import org.apps.simpenpass.data.source.remoteData.RemoteGroupDataSources
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GroupRepositoryMockTest {
    private val apiMockEngine = ApiMocking()
    private val groupDataSample = GroupDetailsSample()
    private val listUserJoinedGroupSample = ListUserJoinedGroupSample()

//    @Test
//    fun `group data result message test success`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking(groupDataSample.detailGroupData)
//        val remoteGroupDataSources = RemoteGroupDataSources(apiClient)
//
//        var result = remoteGroupDataSources.detailGroupData("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",2,3)
//
//        assertEquals(true,result.success)
//    }
//
//    @Test
//    fun `group data result data test success`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking(groupDataSample.detailGroupData)
//        val remoteGroupDataSources = RemoteGroupDataSources(apiClient)
//
//        var result = remoteGroupDataSources.detailGroupData("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",2,3)
//
//        assertEquals(true,result.data != null)
//    }
//
//
//    @Test
//    fun `group data result message test error`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking(groupDataSample.detailGroupData)
//        val remoteGroupDataSources = RemoteGroupDataSources(apiClient)
//
//        var result = remoteGroupDataSources.detailGroupData("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",2,3)
//
//        assertEquals(false,result.success)
//    }
//
//
//    @Test
//    fun `list group joined data result test`() = runTest {
//        val apiClient = apiMockEngine.setupApiMocking(listUserJoinedGroupSample.data)
//        val remoteGroupDataSources = RemoteGroupDataSources(apiClient)
//
//        var result = remoteGroupDataSources.listJoinedGroupBasedOnUser("2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc",1)
//
//        assertEquals(false,result.data?.isEmpty())
//    }
//
//    @Test
//    fun `get Data Pass Group by id`() = runTest {
//
//        var result = ""
//
//        assertTrue(result.isNotEmpty())
//    }
}