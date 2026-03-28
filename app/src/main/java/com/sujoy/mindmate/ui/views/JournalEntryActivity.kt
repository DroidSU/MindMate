package com.sujoy.mindmate.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.views.screens.JournalEntryScreen
import com.sujoy.mindmate.ui.vm.JournalEntryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalEntryActivity : ComponentActivity() {

    private val viewModel: JournalEntryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {

                val uiState = viewModel.uiState.collectAsState()
                val entryText = viewModel.entryText.collectAsState()
                val selectedMood = viewModel.selectedMood.collectAsState()


                JournalEntryScreen(
                    uiState = uiState.value,
                    textContent = entryText.value,
                    selectedMood = selectedMood.value,
                    onTextChange = {
                        viewModel.onEntryTextChanged(it)
                    },
                    onMoodSelected = {
                        viewModel.onMoodSelected(it)
                    },
                    onSaveClick = {
                        viewModel.saveEntry()
                        finish()
                    }
                )
            }
        }
    }
}