package com.aryanto.storyappfinal.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TokenManager.PREF_NAME)

class TokenManager private constructor(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    companion object {
        const val PREF_NAME = "token_prefs"
        val TOKEN = stringPreferencesKey("token")
        val SESSION = booleanPreferencesKey("session")

        @Volatile
        private var instance: TokenManager? = null

        fun getInstance(context: Context): TokenManager {
            return instance ?: synchronized(this) {
                instance ?: TokenManager(context).also { instance = it }
            }
        }

    }

    suspend fun saveTokenAndSession(token: String, session: Boolean) {
        dataStore.edit { prefs ->
            prefs[TOKEN] = token
            prefs[SESSION] = session
        }
    }

    suspend fun getTokenAndSession(): Pair<String?, Boolean?> {
        val prefs = dataStore.data.first()
        val token = prefs[TOKEN]
        val session = prefs[SESSION]
        return Pair(token, session)
    }

    suspend fun clearTokenAndSession() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN)
            prefs.remove(SESSION)
        }
    }


}