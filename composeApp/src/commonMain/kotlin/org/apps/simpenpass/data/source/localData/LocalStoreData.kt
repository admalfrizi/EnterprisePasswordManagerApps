package org.apps.simpenpass.data.source.localData

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.apps.simpenpass.models.LocalUserStore
import org.apps.simpenpass.models.UserData

class LocalStoreData(
    private val dataStore: DataStore<Preferences>
) : DataPrefFunc {
    companion object {
        private val TOKEN_USER = stringPreferencesKey("token_user")
        private val NAME_USER = stringPreferencesKey("name_user")
        private val EMAIL_USER = stringPreferencesKey("email_user")
    }

    override suspend fun saveUserData(user: UserData) {
        dataStore.edit { pref ->
            pref[NAME_USER] = user.name
            pref[EMAIL_USER] = user.email
        }
    }

    override suspend fun saveUserToken(token: String) {
        dataStore.edit { pref ->
            pref[TOKEN_USER] = token
        }
    }

    override suspend fun getToken(): String? {
        val tokenData = dataStore.data.map { prefs ->
            prefs[TOKEN_USER]
        }.first()

        return tokenData
    }

    override suspend fun getUserData(): LocalUserStore {
        val userData = dataStore.data.first()
        val result = LocalUserStore(name = userData[NAME_USER] ?: "", email = userData[EMAIL_USER] ?: "")

        return result
    }

    override suspend fun clearToken() {
        dataStore.edit { pref ->
            pref.remove(TOKEN_USER)
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(NAME_USER)
            preferences.remove(EMAIL_USER)
        }
    }

}