package com.sujoy.mindmate.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class JournalEntryViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(AppUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _entryText = MutableStateFlow("")
    val entryText = _entryText.asStateFlow()

    private val _selectedMood = MutableStateFlow(MoodsEnum.NEUTRAL)
    val selectedMood = _selectedMood.asStateFlow()

    fun onEntryTextChanged(newText: String) {
        _entryText.value = newText
    }

    fun onMoodSelected(mood: MoodsEnum) {
        _selectedMood.value = mood
    }

    fun saveEntry() {
        if (_entryText.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = AppUiState.Loading

            val newItem = JournalItemDBModel(
                id = UUID.randomUUID().toString(),
                content = _entryText.value,
                mood = _selectedMood.value,
                timeStamp = System.currentTimeMillis(),
                analyzedId = ""
            )

            databaseRepository.saveJournalItem(newItem)

            _entryText.value = ""
            _selectedMood.value = MoodsEnum.NEUTRAL
            _uiState.value = AppUiState.Success
        }
    }
}
