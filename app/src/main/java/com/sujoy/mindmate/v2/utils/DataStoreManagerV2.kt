package com.sujoy.mindmate.v2.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sujoy.mindmate.v2.data.models.MoodProviderV2
import com.sujoy.mindmate.v2.data.models.MoodV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "mindmate_datastore_v2")

class DataStoreManagerV2(private val context: Context) {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val SELECTED_MOOD_ID_KEY = intPreferencesKey("selected_mood_id")
    }

    val selectedMoodFlow: Flow<MoodV2?> = context.dataStore.data
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
        return "" // Placeholder for other functionality
    }
}
