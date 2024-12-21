package api_testing

import data_sample.GroupDetailsSample

class PassDataGroupApiTest {
    private val groupDetailsSample = GroupDetailsSample()
    private val apiMockEngine = ApiMocking()

//    @Test
//    fun `get pass data group detail from api`() = runBlocking {
//        val apiClient = apiMockEngine.setupApiMocking(groupDetailsSample.passDataGroupId)
//
//
//        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
//        val groupId = 7
//
//        val response = apiClient.get(Constants.BASE_API_URL+"loadDataPassGroupById/$groupId"){
//            contentType(ContentType.Application.Json)
//            header(HttpHeaders.Authorization, "Bearer $token")
//            parameter("passDataGroupId", 3)
//        }
//
//        println(response.bodyAsText())
//
//        assertEquals(baseReponseSample(groupDetailsSample.passDataGroupId), response.bodyAsText())
//    }
//
//    @Test
//    fun `get delete pass data group from api`() = runBlocking {
//        val apiClient = apiMockEngine.setupApiMocking(groupDetailsSample.passDataGroupId)
//
//
//        val token = "2|DKWA4gE7hi09GKIDWqJFjAL3MlZdEtOJgAAJiQ6Je0d3addc"
//        val groupId = 7
//
//        val response = apiClient.delete(Constants.BASE_API_URL+"deletePassDataGroup/$groupId"){
//            contentType(ContentType.Application.Json)
//            header(HttpHeaders.Authorization, "Bearer $token")
//            parameter("passDataGroupId", 3)
//        }
//
//        println(response.bodyAsText())
//
//        assertEquals(baseReponseSample(groupDetailsSample.passDataGroupId), response.bodyAsText())
//    }

}