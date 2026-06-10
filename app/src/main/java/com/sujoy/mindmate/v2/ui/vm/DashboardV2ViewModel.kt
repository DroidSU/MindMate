package com.sujoy.mindmate.v2.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.v1.utils.ConstantsManager
import com.sujoy.mindmate.v2.data.models.MoodLog
import com.sujoy.mindmate.v2.data.models.MoodV2
import com.sujoy.mindmate.v2.data.repositories.DatabaseRepositoryV2
import com.sujoy.mindmate.v2.utils.DataStoreManagerV2
import com.sujoy.mindmate.v2.utils.UtilityMethodsV2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardV2ViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepositoryV2,
    private val dataStoreManager: DataStoreManagerV2
) : ViewModel() {

    private val _userName: MutableStateFlow<String> = MutableStateFlow("Sujoy")
    val userName = _userName.asStateFlow()

    private val _currentMood = MutableStateFlow<MoodV2?>(null)
    val currentMood: StateFlow<MoodV2?> = _currentMood.asStateFlow()

    init {
        getCurrentMood()
    }

    private fun getCurrentMood() {
        viewModelScope.launch {
            dataStoreManager.selectedMoodFlow.collect { mood ->
                _currentMood.value = mood
            }
        }
    }

    fun storeSelectedMood(mood: MoodV2) {
        viewModelScope.launch {
            dataStoreManager.saveSelectedMood(mood)
            try {
                val moodLog = MoodLog(
                    id = mood.id.toLong(),
                    timestamp = System.currentTimeMillis(),
                    dateString = UtilityMethodsV2.getFormattedDate(
                        System.currentTimeMillis(),
                        "yyyy-MM-dd"
                    ),
                    mood = mood.moodString,
                    moodScore = mood.graphValue.toInt(),
                    emotionalTag = null
                )
                databaseRepository.insertMoodLog(moodLog)
            } catch (ex: Exception) {
                Log.e(ConstantsManager.APP_TAG_V2, ex.message.toString())
            }
        }
    }
}
