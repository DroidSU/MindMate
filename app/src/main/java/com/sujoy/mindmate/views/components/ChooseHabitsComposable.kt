package com.sujoy.mindmate.views.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.ui.theme.LocalGradientColors
import com.sujoy.mindmate.ui.theme.MindMateTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseHabits(
    onContinue: () -> Unit,
    onBack: () -> Unit,
    selectedHabits: Set<String>,
    toggleHabit: (String) -> Unit,
) {
    val habitSuggestions = remember {
        mutableStateListOf(
            "Workout",
            "Meditation",
            "Cook dinner",
            "Write",
            "Sleep on time",
            "No social scrolling"
        )
    }
    var showCustomHabitDialog by remember { mutableStateOf(false) }

    if (showCustomHabitDialog) {
        var customHabit by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showCustomHabitDialog = false },
            title = { Text("Add a custom habit") },
            text = {
                OutlinedTextField(
                    value = customHabit,
                    onValueChange = { customHabit = it },
                    label = { Text("Habit name") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (customHabit.isNotBlank()) {
                            if (!habitSuggestions.contains(customHabit)) {
                                habitSuggestions.add(customHabit)
                            }
                            // Only add if not already selected and within the limit
                            if (customHabit !in selectedHabits && selectedHabits.size < 3) {
                                toggleHabit(customHabit)
                            }
                            showCustomHabitDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { showCustomHabitDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(36.dp)
                .align(alignment = Alignment.TopStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 36.dp, start = 24.dp, end = 24.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Which habits matter most?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pick up to 3 habits to protect.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                habitSuggestions.forEach { habit ->
                    SuggestionChip(
                        text = habit,
                        isSelected = habit in selectedHabits,
                        onClick = {
                            if (habit !in selectedHabits && selectedHabits.size >= 3) {
                                // Do nothing if trying to add more than 3
                            } else {
                                toggleHabit(habit)
                            }
                        }
                    )
                }
                SuggestionChip(
                    text = "+ Add custom",
                    isSelected = false,
                    onClick = { showCustomHabitDialog = true }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Pro Tip: Choose the ones you actually try to keep.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                fontWeight = FontWeight.SemiBold
            )
        }

        AnimatedVisibility(
            visible = selectedHabits.isNotEmpty(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(64.dp)
                    .shadow(12.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                LocalGradientColors.current.buttonStart,
                                LocalGradientColors.current.buttonEnd
                            )
                        )
                    )
                    .clickable(onClick = onContinue)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next Step: Pick Moods",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChooseHabitsPreview() {
    MindMateTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
        ) {
            ChooseHabits(
                onContinue = {},
                onBack = {},
                selectedHabits = setOf("Workout", "Write"),
                toggleHabit = {}
            )
        }
    }
}
