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
        const val TOKEN = "token"
        const val SESSION = "session"

        @Volatile
        private var instance: TokenManager? = null

        fun getInstance(context: Context): TokenManager = instance ?: synchronized(this) {
            instance ?: TokenManager(context).also { instance = it }
        }

    }

    suspend fun getToken(): String? {
        val token = stringPreferencesKey(TOKEN)
        val prefs = dataStore.data.first()
        return prefs[token]
    }

    suspend fun getSession(): Boolean {
        val session = booleanPreferencesKey(SESSION)
        val prefs = dataStore.data.first()
        return prefs[session] ?: false
    }

    suspend fun saveTokenSession(token: String, isLoggedIn: Boolean) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(TOKEN)] = token
            prefs[booleanPreferencesKey(SESSION)] = isLoggedIn
        }
    }

    suspend fun clearTokenAndSession() {
        dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey(TOKEN))
            prefs.remove(booleanPreferencesKey(SESSION))
        }
    }


}