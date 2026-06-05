package com.sujoy.mindmate.v1.ui.views.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.ui.designsystem.components.MindMateCard
import com.sujoy.mindmate.ui.designsystem.theme.MindMateTheme
import com.sujoy.mindmate.ui.vm.StatsPeriod

@Composable
fun MoodStatsScreen(
    dailyStats: Map<Long, Float>,
    selectedPeriod: StatsPeriod,
    onPeriodSelected: (StatsPeriod) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = selectedPeriod.ordinal,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 0.dp,
            divider = {}
        ) {
            StatsPeriod.entries.forEach { period ->
                Tab(
                    selected = selectedPeriod == period,
                    onClick = { onPeriodSelected(period) },
                    text = {
                        Text(
                            text = when (period) {
                                StatsPeriod.SEVEN_DAYS -> "7 Days"
                                StatsPeriod.FOURTEEN_DAYS -> "14 Days"
                                StatsPeriod.THIRTY_DAYS -> "30 Days"
                            },
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(MindMateTheme.spacing.Medium))

        MindMateCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            // Placeholder for a premium chart
            Text(
                text = "Mood Analytics Visualization",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(MindMateTheme.spacing.Large)
            )
            // In a real app, I'd integrate a charting library or draw a custom one here
            Text(
                text = "Your mood has been improving by 15% over the last ${
                    selectedPeriod.name.lowercase().replace("_", " ")
                }.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
