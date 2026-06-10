package com.sujoy.mindmate.v1.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import com.sujoy.mindmate.v1.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
        observeJournalItems()
    }

    private fun loadHomeData() {
        val currentDate = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
        val greeting = when (java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good morning"
            in 12..16 -> "Good afternoon"
            else -> "Good evening"
        }

        _uiState.update {
            it.copy(
                date = currentDate,
                greeting = greeting,
                habits = listOf(
                    HabitUiModel("1", "Sleep", "🌙", "7h 30m", true),
                    HabitUiModel("2", "Exercise", "🏃", "30 min", true),
                    HabitUiModel("3", "Meditation", "🧘", "10 min", false),
                    HabitUiModel("4", "Water", "💧", "4 / 8 glasses", true)
                )
            )
        }
    }

    private fun observeJournalItems() {
        viewModelScope.launch {
            databaseRepository.getFirst10JournalItems().collect { items ->
                _uiState.update { it.copy(recentJournal = items.firstOrNull()) }
            }
        }
    }

    fun onMoodSelected(mood: MoodsEnum) {
        _uiState.update { it.copy(selectedMood = mood) }
    }
}
