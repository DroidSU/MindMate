package com.sujoy.mindmate.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

enum class StatsPeriod {
    SEVEN_DAYS, FOURTEEN_DAYS, THIRTY_DAYS
}

@HiltViewModel
class MoodStatsViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _selectedPeriod = MutableStateFlow(StatsPeriod.SEVEN_DAYS)
    val selectedPeriod = _selectedPeriod.asStateFlow()

    private val averageMoods = databaseRepository.getAllAverageMoods()

    val chartData = combine(averageMoods, _selectedPeriod) { moods, period ->
        val limit = when (period) {
            StatsPeriod.SEVEN_DAYS -> 7
            StatsPeriod.FOURTEEN_DAYS -> 14
            StatsPeriod.THIRTY_DAYS -> 30
        }

        // Take last 'limit' unique dates (they are already sorted DESC from DAO)
        val filteredMoods = moods.take(limit).reversed()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        filteredMoods.associate { mood ->
            val date = sdf.parse(mood.date)
            (date?.time ?: 0L) to mood.avgMoodScore
        }.toSortedMap()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun setPeriod(period: StatsPeriod) {
        _selectedPeriod.value = period
    }
}
