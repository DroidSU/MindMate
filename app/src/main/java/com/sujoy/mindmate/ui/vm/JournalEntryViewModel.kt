package com.sujoy.mindmate.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import com.sujoy.mindmate.data.repositories.MlModelRepository
import com.sujoy.mindmate.utils.DataStoreManager
import com.sujoy.mindmate.utils.UtilityMethods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalEntryViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val mlModelRepository: MlModelRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(AppUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _entryText = MutableStateFlow("")
    val entryText = _entryText.asStateFlow()

    private val _selectedMood = MutableStateFlow(MoodsEnum.NEUTRAL)
    val selectedMood = _selectedMood.asStateFlow()

    private val _moodScore = MutableStateFlow(0.5f)
    val moodScore = _moodScore.asStateFlow()

    private val journalId = MutableStateFlow("")
    private val userName = MutableStateFlow("")

    private val _analyzedMood = MutableStateFlow(JournalAnalyzedDbModel("", "", 0f, "", "", 0))
    val analyzedMood = _analyzedMood.asStateFlow()


    init {
        viewModelScope.launch {
            journalId.value = UtilityMethods.generateUniqueJournalId()
            userName.value = dataStoreManager.getUsername()
        }
    }


    fun onEntryTextChanged(newText: String) {
        _entryText.value = newText
    }

    fun onMoodSelected(mood: MoodsEnum) {
        _selectedMood.value = mood
    }

    fun onMoodScoreChanged(score: Float) {
        _moodScore.value = score
    }

    fun analyzeEntry() {
        if (_entryText.value.isBlank()) return

        val content = _entryText.value
        viewModelScope.launch {
            val result = mlModelRepository.analyzeSentimentLocal(content, journalId.value)

            if (result.isSuccess) {
                val analyzedItem = result.getOrNull()!!

                _analyzedMood.value = analyzedItem
                databaseRepository.saveAnalysisAndUpdateScore(analyzedItem)

                onAnalysisSuccess()
                _uiState.value = AppUiState.Success

            } else {
                _uiState.value =
                    AppUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun onAnalysisSuccess() {
        _entryText.value = ""
        _selectedMood.value = MoodsEnum.NEUTRAL
        _moodScore.value = 0.5f
        _uiState.value = AppUiState.Success
    }

    fun saveEntry() {
        if (_entryText.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = AppUiState.Loading

            val newItem = JournalItemDBModel(
                id = journalId.value,
                content = _entryText.value,
                mood = _selectedMood.value,
                timeStamp = System.currentTimeMillis(),
                analyzedId = "",
                sentimentScore = 0f,
                moodScore = _moodScore.value
            )

            databaseRepository.saveJournalItem(newItem)

            analyzeEntry()
        }
    }
}
