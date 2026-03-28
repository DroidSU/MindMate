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
import com.sujoy.mindmate.ui.views.screens.TimelineScreen
import com.sujoy.mindmate.ui.vm.TimelineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimelineActivity : ComponentActivity() {

    private val viewModel: TimelineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {

                val uiState by viewModel.uiState.collectAsState()
                val journalItems by viewModel.journalItemList.collectAsState()

                TimelineScreen(
                    uiState,
                    journalItems,
                    onAddEntryClick = {
                        startActivity(Intent(this, JournalEntryActivity::class.java))
                    }
                )
            }
        }
    }
}