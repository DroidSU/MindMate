package com.sujoy.mindmate.v1.ui.vm

import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum

data class HomeUiState(
    val username: String = "Sujoy",
    val greeting: String = "Good morning",
    val date: String = "Friday, June 5",
    val selectedMood: MoodsEnum? = null,
    val habits: List<HabitUiModel> = emptyList(),
    val recentJournal: JournalItemDBModel? = null,
    val isLoading: Boolean = false
)

data class HabitUiModel(
    val id: String,
    val name: String,
    val icon: String, // String for emoji or resource name
    val progress: String,
    val isCompleted: Boolean
)
