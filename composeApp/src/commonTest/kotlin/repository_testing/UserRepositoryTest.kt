package repository_testing

import androidx.compose.runtime.mutableStateOf
import api_testing.ApiMocking
import kotlinx.coroutines.runBlocking
import org.apps.simpenpass.data.repository.UserRepository
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.data.source.remoteData.RemoteUserSources
import org.apps.simpenpass.models.request.RegisterRequest
import org.apps.simpenpass.models.user_data.UserData
import org.apps.simpenpass.utils.NetworkResult
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryTest : KoinTest {

//    private val apiMockEngine = ApiMocking()
//    private val apiClient = apiMockEngine.client
//
//    @BeforeTest
//    fun startKoin() {
//        startApp()
//    }
//
//    @AfterTest
//    fun stopKoin() {
//        stopApp()
//    }
//
//    @Test
//    fun `register result message test success`() = runBlocking {
//        val remoteUserSources = RemoteUserSources(apiClient)
//        val localStoreData = LocalStoreData(get())
//        val userRepo = UserRepository(remoteUserSources,localStoreData)
//
//        val result = mutableStateOf<UserData>(UserData(0,"",""))
//
//        apiMockEngine.givenSuccess()
//
//        userRepo.register(RegisterRequest("","","","")).collect { res ->
//            when(res){
//                is NetworkResult.Success -> {
//                    result.value = res.data!!
//                }
//                is NetworkResult.Error -> {}
//                is NetworkResult.Loading -> {}
//            }
//        }
//
//        assertEquals("", result.value.name)
//    }

}