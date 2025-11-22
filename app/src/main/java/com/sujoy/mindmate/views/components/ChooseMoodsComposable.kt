package com.sujoy.mindmate.views.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.models.MoodDataModel
import com.sujoy.mindmate.ui.theme.LocalGradientColors
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.vm.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseMoods(onContinue: () -> Unit, onBack: () -> Unit, viewModel: OnboardingViewModel) {
    val moods = listOf(
        MoodDataModel("Happy", "😊", "Feeling joyful and positive"),
        MoodDataModel("Anxious", "😬", "Feeling worried, nervous, or uneasy"),
        MoodDataModel("Tired", "😴", "Feeling sleepy or drained of energy"),
        MoodDataModel("Lonely", "😔", "Feeling sad from being alone"),
        MoodDataModel("Stressed", "😣", "Feeling overwhelmed and under pressure"),
        MoodDataModel("Bored", "😐", "Feeling unoccupied and uninterested"),
        MoodDataModel("Angry", "😠", "Feeling annoyed or displeasure"),
        MoodDataModel("Calm", "😌", "Feeling peaceful and untroubled")
    )

    val selectedMoods by viewModel.selectedMoods.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.TopStart)
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
                .padding(top = 40.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Which moods break your habits?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pick moods that usually make you skip those habits.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            ) {
                items(moods) { mood ->
                    MoodCard(
                        mood = mood,
                        isSelected = mood.name in selectedMoods,
                        onClick = {
                            if (mood.name in selectedMoods) {
                                viewModel.removeMood(mood.name)
                            } else {
                                viewModel.addMood(mood.name)
                            }
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = selectedMoods.isNotEmpty(),
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
                    contentDescription = "Next Step: Set Reminders",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChooseMoodsPreview() {
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
            ChooseMoods(onContinue = {}, onBack = {}, viewModel = viewModel())
        }
    }
}
