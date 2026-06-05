package com.sujoy.mindmate.v1.ui.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.ui.designsystem.components.JournalFilterChip
import com.sujoy.mindmate.ui.designsystem.components.JournalHeader
import com.sujoy.mindmate.ui.designsystem.components.JournalSearchBar
import com.sujoy.mindmate.ui.designsystem.components.JournalTimeline
import com.sujoy.mindmate.ui.designsystem.components.MindMateBottomBar
import com.sujoy.mindmate.ui.designsystem.theme.MindMateTheme

@Composable
fun JournalScreen(
    entries: List<JournalItemDBModel>,
    onAddEntryClick: () -> Unit,
    onNavigate: (String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Mood", "Habits", "Work", "Personal", "Health")

    Scaffold(
        bottomBar = {
            MindMateBottomBar(
                currentRoute = "journal",
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEntryClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Entry",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = MindMateTheme.spacing.Medium)
        ) {
            item {
                JournalHeader()
                JournalSearchBar()
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.Medium))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MindMateTheme.spacing.Small),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filters) { filter ->
                        JournalFilterChip(
                            text = filter,
                            isSelected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MindMateTheme.spacing.Large))
            }

            item {
                JournalTimeline(entries = entries)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    MindMateTheme {
        JournalScreen(
            entries = listOf(
                JournalItemDBModel(
                    id = "1",
                    title = "Great Day",
                    content = "Had an amazing workout this morning and felt so energized afterward...",
                    mood = MoodsEnum.ENERGETIC,
                    timeStamp = System.currentTimeMillis(),
                    tags = listOf("Health", "Exercise", "Gratitude"),
                    imageUrl = "https://example.com/image.jpg"
                ),
                JournalItemDBModel(
                    id = "2",
                    title = "Calm Evening",
                    content = "Meditation before bed really helps me relax and unwind after a hectic day at work.",
                    mood = MoodsEnum.CALM,
                    timeStamp = System.currentTimeMillis() - 86400000,
                    tags = listOf("Mindfulness", "Wellness"),
                    imageUrl = "https://example.com/image2.jpg"
                )
            ),
            onAddEntryClick = {},
            onNavigate = {}
        )
    }
}
