package com.sujoy.mindmate.v1.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.views.screens.HomeScreen
import com.sujoy.mindmate.ui.views.screens.JournalScreen
import com.sujoy.mindmate.ui.vm.HomeViewModel
import com.sujoy.mindmate.ui.vm.TimelineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val timelineViewModel: TimelineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                var currentRoute by remember { mutableStateOf("home") }

                when (currentRoute) {
                    "home" -> {
                        val uiState by homeViewModel.uiState.collectAsState()
                        HomeScreen(
                            uiState = uiState,
                            onMoodSelected = { homeViewModel.onMoodSelected(it) },
                            onAddNoteClick = {
                                startActivity(Intent(this, JournalEntryActivity::class.java))
                            },
                            onStartWritingClick = {
                                startActivity(Intent(this, JournalEntryActivity::class.java))
                            },
                            onSeeAllHabitsClick = { /* TODO */ },
                            onSeeAllJournalClick = { currentRoute = "journal" },
                            onJournalClick = { /* TODO */ },
                            onNavigate = { currentRoute = it }
                        )
                    }

                    "journal" -> {
                        val entries by timelineViewModel.journalItemList.collectAsState()
                        JournalScreen(
                            entries = entries,
                            onAddEntryClick = {
                                startActivity(Intent(this, JournalEntryActivity::class.java))
                            },
                            onNavigate = { currentRoute = it }
                        )
                    }

                    else -> {
                        // Handle other routes
                    }
                }
            }
        }
    }
}
