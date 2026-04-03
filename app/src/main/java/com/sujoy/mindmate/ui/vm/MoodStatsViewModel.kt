package com.sujoy.mindmate.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

enum class StatsPeriod {
    SEVEN_DAYS, ALL
}

@HiltViewModel
class MoodStatsViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _journalItems = MutableStateFlow<List<JournalItemDBModel>>(emptyList())
    val journalItems: StateFlow<List<JournalItemDBModel>> = _journalItems.asStateFlow()

    private val _selectedPeriod = MutableStateFlow(StatsPeriod.SEVEN_DAYS)
    val selectedPeriod = _selectedPeriod.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getJournalItems().collect {
                _journalItems.value = it
            }
        }
    }

    fun setPeriod(period: StatsPeriod) {
        _selectedPeriod.value = period
    }

    /**
     * Maps MoodsEnum to a numerical value for graphing.
     * Scale: -1.0 (Most Negative) to 1.0 (Most Positive)
     */
    private fun MoodsEnum.toGraphValue(): Float {
        return when (this) {
            MoodsEnum.HAPPY -> 1.0f
            MoodsEnum.MOTIVATED -> 0.8f
            MoodsEnum.RELAXED -> 0.5f
            MoodsEnum.NEUTRAL -> 0.0f
            MoodsEnum.ANXIOUS -> -0.3f
            MoodsEnum.STRESSED -> -0.6f
            MoodsEnum.SAD -> -0.8f
            MoodsEnum.ANGRY -> -1.0f
        }
    }

    val chartData = combine(journalItems, _selectedPeriod) { items, period ->
        val filteredItems = when (period) {
            StatsPeriod.SEVEN_DAYS -> {
                val sevenDaysAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
                items.filter { it.timeStamp >= sevenDaysAgo }
            }

            StatsPeriod.ALL -> items
        }.sortedBy { it.timeStamp }

        if (filteredItems.isEmpty()) return@combine emptyMap<Long, Float>()

        val calendar = Calendar.getInstance()
        val dailyStats = mutableMapOf<Long, MutableList<Float>>()

        if (period == StatsPeriod.SEVEN_DAYS) {
            // Pre-fill last 7 days to ensure they appear on chart
            val now = Calendar.getInstance()
            now.set(Calendar.HOUR_OF_DAY, 0)
            now.set(Calendar.MINUTE, 0)
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)

            for (i in 0 until 7) {
                val day = now.clone() as Calendar
                day.add(Calendar.DAY_OF_YEAR, -i)
                dailyStats[day.timeInMillis] = mutableListOf()
            }
        }

        filteredItems.forEach { item ->
            calendar.timeInMillis = item.timeStamp
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val dayStart = calendar.timeInMillis

            // Map the specific MoodsEnum to its graph value
            val graphValue = item.mood.toGraphValue()

            if (period == StatsPeriod.ALL) {
                dailyStats.getOrPut(dayStart) { mutableListOf() }.add(graphValue)
            } else {
                if (dailyStats.containsKey(dayStart)) {
                    dailyStats[dayStart]?.add(graphValue)
                }
            }
        }

        dailyStats.mapValues { (_, values) ->
            if (values.isEmpty()) 0f else values.average().toFloat()
        }.toSortedMap()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
}
