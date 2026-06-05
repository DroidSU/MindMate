package com.sujoy.mindmate.v1.ui.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sujoy.mindmate.v1.data.models.JournalItemDBModel
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.designsystem.components.DailyReflectionCard
import com.sujoy.mindmate.v1.ui.designsystem.components.HabitSummaryCard
import com.sujoy.mindmate.v1.ui.designsystem.components.HomeHeader
import com.sujoy.mindmate.v1.ui.designsystem.components.InsightHeroCard
import com.sujoy.mindmate.v1.ui.designsystem.components.MindMateBottomBar
import com.sujoy.mindmate.v1.ui.designsystem.components.MindMateSectionHeader
import com.sujoy.mindmate.v1.ui.designsystem.components.MoodSelectorCard
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme
import com.sujoy.mindmate.v1.ui.vm.HabitUiModel
import com.sujoy.mindmate.v1.ui.vm.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onMoodSelected: (MoodsEnum) -> Unit,
    onAddNoteClick: () -> Unit,
    onStartWritingClick: () -> Unit,
    onSeeAllHabitsClick: () -> Unit,
    onSeeAllJournalClick: () -> Unit,
    onJournalClick: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            MindMateBottomBar(
                currentRoute = "home",
                onNavigate = onNavigate
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(MindMateTheme.spacing.Large) // Increased from Medium
        ) {
            item {
                HomeHeader(
                    greeting = uiState.greeting,
                    name = uiState.username,
                    date = uiState.date
                )
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.Large))
            }

            item {
                MoodSelectorCard(
                    selectedMood = uiState.selectedMood,
                    onMoodSelected = onMoodSelected,
                    onAddNoteClick = onAddNoteClick
                )
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.ExtraLarge))
            }

            item {
                InsightHeroCard(
                    title = "You felt noticeably calmer on days when you exercised.",
                    description = "This pattern shows up regularly.",
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.ExtraLarge))
            }

            item {
                DailyReflectionCard(
                    question = "What gave you energy today?",
                    onStartWritingClick = onStartWritingClick
                )
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.ExtraLarge))
            }

            item {
                MindMateSectionHeader(
                    title = "Today's Habits",
                    actionText = "See all >",
                    onActionClick = onSeeAllHabitsClick
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MindMateTheme.spacing.Medium)
                ) {
                    items(uiState.habits) { habit ->
                        HabitSummaryCard(habit = habit)
                    }
                }
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.ExtraLarge))
            }

            item {
                MindMateSectionHeader(
                    title = "Recent Journal",
                    actionText = "See all >",
                    onActionClick = onSeeAllJournalClick
                )
//                uiState.recentJournal?.let { journal ->
//                    RecentJournalCard(
//                        journalItem = journal,
//                        onClick = onJournalClick
//                    )
//                }
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.Large))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MindMateTheme {
        HomeScreen(
            uiState = HomeUiState(
                username = "Sujoy",
                greeting = "Good morning",
                date = "Friday, June 5",
                selectedMood = MoodsEnum.HAPPY,
                habits = listOf(
                    HabitUiModel("1", "Sleep", "🌙", "7h 30m", true),
                    HabitUiModel("2", "Exercise", "🏃", "30 min", true),
                    HabitUiModel("3", "Meditation", "🧘", "10 min", false),
                    HabitUiModel("4", "Water", "💧", "4/8", true)
                ),
                recentJournal = JournalItemDBModel(
                    id = "1",
                    content = "Had an amazing workout this morning and felt so energized throughout the day...",
                    mood = MoodsEnum.HAPPY,
                    timeStamp = System.currentTimeMillis()
                )
            ),
            onMoodSelected = {},
            onAddNoteClick = {},
            onStartWritingClick = {},
            onSeeAllHabitsClick = {},
            onSeeAllJournalClick = {},
            onJournalClick = {},
            onNavigate = {}
        )
    }
}

