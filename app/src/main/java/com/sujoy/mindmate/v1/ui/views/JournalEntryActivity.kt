package com.sujoy.mindmate.v1.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sujoy.mindmate.v1.ui.theme.MindMateTheme
import com.sujoy.mindmate.v1.ui.views.screens.JournalEntryScreen
import com.sujoy.mindmate.v1.ui.vm.JournalEntryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalEntryActivity : ComponentActivity() {

    private val viewModel: JournalEntryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {

                val uiState by viewModel.uiState.collectAsState()
                val entryText by viewModel.entryText.collectAsState()
                val selectedMood by viewModel.selectedMood.collectAsState()
                val moodScore by viewModel.moodScore.collectAsState()
                val analyzedMoodObject by viewModel.analyzedMood.collectAsState()


                JournalEntryScreen(
                    uiState = uiState,
                    textContent = entryText,
                    selectedMood = selectedMood,
                    moodScore = moodScore,
                    analyzedMoodObject = analyzedMoodObject,
                    onTextChange = {
                        viewModel.onEntryTextChanged(it)
                    },
                    onMoodSelected = {
                        viewModel.onMoodSelected(it)
                    },
                    onMoodScoreChanged = { score ->
                        viewModel.onMoodScoreChanged(score)
                    },
                    onSaveClick = {
                        viewModel.saveEntry()
                    },
                    onCloseDialog = {
                        finish()
                    }
                )
            }
        }
    }
}