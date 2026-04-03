package com.sujoy.mindmate.ui.views.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.vm.StatsPeriod
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodColor

@Composable
fun HomeScreen2(
    journalItems: List<JournalItemDBModel>,
    dailyStats: Map<Long, Float>,
    selectedPeriod: StatsPeriod,
    onPeriodSelected: (StatsPeriod) -> Unit,
    onAddEntryClick: () -> Unit
) {
    val latestMoodColor = if (journalItems.isNotEmpty()) {
        getMoodColor(journalItems.first().mood)
    } else {
        MaterialTheme.colorScheme.primary
    }

    val animatedAuraColor by animateColorAsState(
        targetValue = latestMoodColor,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "auraColor"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        // --- AMBIENT AURA ---
        Canvas(modifier = Modifier
            .fillMaxSize()
            .blur(100.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(animatedAuraColor.copy(alpha = 0.12f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.2f),
                    radius = size.width * 1.5f
                )
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(animatedAuraColor.copy(alpha = 0.08f), Color.Transparent),
                    center = Offset(size.width * 0.2f, size.height * 0.8f),
                    radius = size.width * 1.2f
                )
            )
        }

        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = onAddEntryClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(20.dp),
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp)
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("New Moment", fontWeight = FontWeight.Bold)
                }
            }
        ) { innerPadding ->
            val timelineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
                    .drawBehind {
                        // Vertical timeline line
                        drawLine(
                            color = timelineColor,
                            start = Offset(40.dp.toPx(), 450.dp.toPx()),
                            end = Offset(40.dp.toPx(), size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
            ) {
                // 1. Creative Header
                item {
                    HomeHeader(userName = "Mindful Friend")
                }

                // 2. Redesigned Mood Stats Section
                item {
                    MoodStatsScreen(
                        dailyStats = dailyStats,
                        selectedPeriod = selectedPeriod,
                        onPeriodSelected = onPeriodSelected
                    )
                }

                // 3. Timeline Label
                item {
                    Text(
                        text = "Recent Reflections",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // 4. Daily Hook
                item {
                    DailyHookStateless(onAddEntryClick)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // 5. Timeline Content
                if (journalItems.isEmpty()) {
                    item {
                        EmptyTimelineStateStateless(onAddEntryClick)
                    }
                } else {
                    itemsIndexed(
                        items = journalItems,
                        key = { _, item -> item.id }
                    ) { _, item ->
                        TimelineMomentStateless(item)
                    }
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
fun HomeHeader(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Hello,",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            letterSpacing = (-1).sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreen2Preview() {
    MindMateTheme {
        HomeScreen2(
            journalItems = emptyList(),
            dailyStats = emptyMap(),
            selectedPeriod = StatsPeriod.SEVEN_DAYS,
            onPeriodSelected = {},
            onAddEntryClick = {}
        )
    }
}
