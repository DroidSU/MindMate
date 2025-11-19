package com.sujoy.mindmate.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_preferences")

class DataStoreManager(private val context: Context) {
    companion object {
        val SELECTED_HABITS_KEY = stringSetPreferencesKey("selected_habits")
        val SELECTED_MOODS_KEY = stringSetPreferencesKey("selected_moods")
        val REMINDER_TYPE_KEY = intPreferencesKey("reminder_type")
        val REMINDER_TIME_KEY = longPreferencesKey("reminder_time")
    }

    suspend fun saveOnboardingSelections(
        selectedHabits: Set<String>,
        selectedMoods: Set<String>,
        reminderType: Int,
        reminderTime: Long
    ) {
        context.datastore.edit { preferences ->
            preferences[SELECTED_HABITS_KEY] = selectedHabits
            preferences[SELECTED_MOODS_KEY] = selectedMoods
            preferences[REMINDER_TYPE_KEY] = reminderType
            preferences[REMINDER_TIME_KEY] = reminderTime
        }
    }

    suspend fun getSelectedHabits(): Set<String> {
        val preferences = context.datastore.data.first()
        return preferences[SELECTED_HABITS_KEY] ?: emptySet()
    }

    suspend fun getSelectedMoods(): Set<String> {
        val preferences = context.datastore.data.first()
        return preferences[SELECTED_MOODS_KEY] ?: emptySet()
    }


    /**
     * If you want to observe changes in real-time (recommended for UI),
     * expose a Flow instead of using a suspend function.
     */
    val selectedHabitsFlow: Flow<Set<String>> = context.datastore.data.map { preferences ->
        // Map the preferences to the specific Set<String> you need
        preferences[SELECTED_HABITS_KEY] ?: emptySet()
    }

    val selectedMoodsFlow: Flow<Set<String>> = context.datastore.data.map { preferences ->
        preferences[SELECTED_MOODS_KEY] ?: emptySet()
    }
}