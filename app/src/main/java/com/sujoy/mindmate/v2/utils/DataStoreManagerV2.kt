package com.sujoy.mindmate.v2.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sujoy.mindmate.v2.data.models.MoodProviderV2
import com.sujoy.mindmate.v2.data.models.MoodV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "mindmate_datastore_v2")

class DataStoreManagerV2(private val context: Context) {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val SELECTED_MOOD_ID_KEY = intPreferencesKey("selected_mood_id")
    }

    val lastSelectedMood: Flow<MoodV2?> = context.dataStore.data
        .map { preferences ->
            val moodId = preferences[SELECTED_MOOD_ID_KEY] ?: -1
            MoodProviderV2.getMoodById(moodId)
        }

    suspend fun saveSelectedMood(mood: MoodV2) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_MOOD_ID_KEY] = mood.id
        }
    }

    suspend fun getUsername(): String {
        return context.dataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: "User"
        }.first()
    }

    suspend fun getUserId(): String {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: "11234"
        }.first()
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }
}
