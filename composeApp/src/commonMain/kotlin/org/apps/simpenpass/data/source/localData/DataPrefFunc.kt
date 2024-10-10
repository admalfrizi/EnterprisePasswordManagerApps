package org.apps.simpenpass.data.source.localData

import org.apps.simpenpass.models.LocalUserStore
import org.apps.simpenpass.models.UserData

interface DataPrefFunc {
    suspend fun saveUserData(user: UserData)
    suspend fun saveUserToken(token: String)
    suspend fun getUserData() : LocalUserStore
    suspend fun clearUserData()
}