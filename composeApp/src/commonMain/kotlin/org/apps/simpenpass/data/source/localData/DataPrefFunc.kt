package org.apps.simpenpass.data.source.localData

import org.apps.simpenpass.models.user_data.LocalUserStore
import org.apps.simpenpass.models.user_data.UserData

interface DataPrefFunc {
    suspend fun saveUserData(user: UserData)
    suspend fun saveUserToken(token: String)
    suspend fun getUserData() : LocalUserStore
    suspend fun clearUserData()
}