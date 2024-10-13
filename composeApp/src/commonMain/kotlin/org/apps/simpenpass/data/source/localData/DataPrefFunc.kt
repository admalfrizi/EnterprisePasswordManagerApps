package org.apps.simpenpass.data.source.localData

import kotlinx.coroutines.flow.Flow
import org.apps.simpenpass.models.user_data.LocalUserStore
import org.apps.simpenpass.models.user_data.UserData

interface DataPrefFunc {
    suspend fun setLoggedInStatus(isLogged : Boolean)
    suspend fun saveUserData(user: UserData)
    suspend fun saveUserToken(token: String)
    suspend fun checkLoggedIn() : Flow<Boolean>
    suspend fun getUserData() : LocalUserStore
    suspend fun clearUserData()
}