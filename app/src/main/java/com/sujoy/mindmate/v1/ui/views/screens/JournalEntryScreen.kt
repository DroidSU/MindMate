package com.sujoy.mindmate.v1.ui.views.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sujoy.mindmate.v1.data.models.AppUiState
import com.sujoy.mindmate.v1.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.designsystem.components.MindMateSectionHeader
import com.sujoy.mindmate.v1.ui.designsystem.components.MindMateTopBar
import com.sujoy.mindmate.v1.ui.designsystem.components.MoodSelector
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryScreen(
    uiState: AppUiState,
    textContent: String,
    selectedMood: MoodsEnum,
    moodScore: Float,
    analyzedMoodObject: JournalAnalyzedDbModel,
    onTextChange: (String) -> Unit,
    onMoodSelected: (MoodsEnum) -> Unit,
    onMoodScoreChanged: (Float) -> Unit,
    onSaveClick: () -> Unit,
    onCloseDialog: () -> Unit,
) {
    Scaffold(
        topBar = {
            MindMateTopBar(
                title = "Reflect",
                navigationIcon = {
                    IconButton(onClick = onCloseDialog) {
                        Icon(Icons.Rounded.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = onSaveClick, enabled = textContent.isNotBlank()) {
                        Icon(Icons.Rounded.Done, contentDescription = "Save")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MindMateTheme.spacing.Medium)
        ) {
            MindMateSectionHeader(title = "How are you feeling?")
            MoodSelector(
                selectedMood = selectedMood,
                onMoodSelected = onMoodSelected
            )

            Spacer(modifier = Modifier.height(MindMateTheme.spacing.Large))

            MindMateSectionHeader(title = "What's on your mind?")
            OutlinedTextField(
                value = textContent,
                onValueChange = onTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = {
                    Text(
                        "Write freely...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}
