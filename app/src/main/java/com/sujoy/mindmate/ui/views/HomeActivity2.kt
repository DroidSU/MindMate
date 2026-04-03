package com.sujoy.mindmate.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.views.screens.HomeScreen2
import com.sujoy.mindmate.ui.vm.MoodStatsViewModel
import com.sujoy.mindmate.ui.vm.TimelineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity2 : ComponentActivity() {

    private val timelineViewModel: TimelineViewModel by viewModels()
    private val moodStatsViewModel: MoodStatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                val journalItems by timelineViewModel.journalItemList.collectAsState()
                val dailyStats by moodStatsViewModel.chartData.collectAsState()
                val selectedPeriod by moodStatsViewModel.selectedPeriod.collectAsState()

                HomeScreen2(
                    journalItems = journalItems,
                    dailyStats = dailyStats,
                    selectedPeriod = selectedPeriod,
                    onPeriodSelected = { moodStatsViewModel.setPeriod(it) },
                    onAddEntryClick = {
                        startActivity(Intent(this@HomeActivity2, JournalEntryActivity::class.java))
                    }
                )
            }
        }
    }
}
