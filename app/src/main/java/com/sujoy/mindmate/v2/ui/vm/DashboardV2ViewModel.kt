package com.sujoy.mindmate.v2.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.v2.data.models.MoodV2
import com.sujoy.mindmate.v2.data.repositories.DatabaseRepositoryV2
import com.sujoy.mindmate.v2.utils.DataStoreManagerV2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
        observeCurrentMood()
    }

    private fun observeCurrentMood() {
        viewModelScope.launch {
            dataStoreManager.selectedMoodFlow.collectLatest { mood ->
                _currentMood.value = mood
            }
        }
    }

    fun storeSelectedMood(mood: MoodV2) {
        viewModelScope.launch {
            dataStoreManager.saveSelectedMood(mood)
            // Optionally also save to databaseRepository here
        }
    }
}
