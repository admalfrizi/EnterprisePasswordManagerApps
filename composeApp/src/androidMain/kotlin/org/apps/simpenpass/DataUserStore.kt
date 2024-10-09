package org.apps.simpenpass

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.apps.simpenpass.data.source.localData.DataPrefFunc
import org.apps.simpenpass.models.LocalUserStore
import org.apps.simpenpass.models.UserData

private val Context.dataStore by preferencesDataStore(name = "user_data")

//class DataUserStore(private val context: Context) : DataPrefFunc {
//    companion object {
//        private val TOKEN_USER = stringPreferencesKey("token_user")
//        private val NAME_USER = stringPreferencesKey("name_user")
//        private val EMAIL_USER = stringPreferencesKey("name_user")
//    }
//
//    override suspend fun saveUserData(user: UserData) {
//        context.dataStore.edit { pref ->
//            pref[NAME_USER] = user.name
//            pref[EMAIL_USER] = user.email
//        }
//    }
//
//    override suspend fun saveUserToken(token: String) {
//        context.dataStore.edit { pref ->
//            pref[NAME_USER] = token
//        }
//    }
//
//    override suspend fun getToken(): String? {
//        val tokenData = context.dataStore.data.map { prefs ->
//            prefs[TOKEN_USER]
//        }.first()
//
//        return tokenData
//    }
//
//    override suspend fun getUserData(): LocalUserStore {
//        val prefs = context.dataStore.data.first()
//        val userData = LocalUserStore(name = prefs[NAME_USER] ?: "", email = prefs[EMAIL_USER] ?: "")
//
//        return userData
//
//    }
//
//    override suspend fun clearToken() {
//        context.dataStore.edit { pref ->
//            pref.remove(TOKEN_USER)
//        }
//    }
//
//    override suspend fun clearUserData() {
//        context.dataStore.edit { preferences ->
//            preferences.remove(NAME_USER)
//            preferences.remove(EMAIL_USER)
//        }
//    }
//}