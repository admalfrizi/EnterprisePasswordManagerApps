package org.apps.simpenpass.data.source.localData

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.apps.simpenpass.models.user_data.LocalUserStore
import org.apps.simpenpass.models.user_data.UserData

class LocalStoreData(
    private val dataStore: DataStore<Preferences>
) : DataPrefFunc {
    companion object {
        private val LOGIN_CHECKED = booleanPreferencesKey("is_login")
        private val TOKEN_USER = stringPreferencesKey("token_user")
        private val ID_USER = intPreferencesKey("id_user")
        private val NAME_USER = stringPreferencesKey("name_user")
        private val EMAIL_USER = stringPreferencesKey("email_user")
    }

    override suspend fun setLoggedInStatus(isLogged: Boolean) {
        dataStore.edit { pref ->
            pref[LOGIN_CHECKED] = isLogged
        }
    }

    override suspend fun saveUserData(user: UserData) {
        dataStore.edit { pref ->
            pref[ID_USER] = user.id
            pref[NAME_USER] = user.name
            pref[EMAIL_USER] = user.email
        }
    }

    override suspend fun saveUserToken(token: String) {
        dataStore.edit { pref ->
            pref[TOKEN_USER] = token
        }
    }

    override suspend fun checkLoggedIn(): Flow<Boolean> {
        val getStatus = dataStore.data
        return getStatus.catch {
            emptyFlow<Boolean>()
        }.map { pref ->
            pref[LOGIN_CHECKED] ?: false
        }
    }

    val getToken : Flow<String> = dataStore.data
        .catch { e ->
            if(e is IOException){
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { prefs ->
            val tokenData = prefs[TOKEN_USER] ?: ""
            tokenData
        }



    override suspend fun getUserData(): LocalUserStore {
        val userData = dataStore.data.first()
        val result = LocalUserStore(
            id = userData[ID_USER], name = userData[NAME_USER] ?: "", email = userData[EMAIL_USER] ?: "")

        return result
    }

    override suspend fun clearUserData() {
        dataStore.edit { pref ->
            pref.remove(TOKEN_USER)
            pref.remove(ID_USER)
            pref.remove(NAME_USER)
            pref.remove(EMAIL_USER)
        }
    }

}