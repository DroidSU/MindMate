package com.sujoy.mindmate.v1.ui.views.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.v1.data.models.AppUiState
import com.sujoy.mindmate.v1.data.models.JournalItemDBModel
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.theme.MindMateTheme
import com.sujoy.mindmate.v1.ui.views.components.HomeScreenHeader
import com.sujoy.mindmate.v1.utils.UtilityMethods
import com.sujoy.mindmate.v1.utils.UtilityMethods.Companion.getMoodColor
import com.sujoy.mindmate.v1.utils.UtilityMethods.Companion.getMoodEmoji

@Composable
fun TimelineScreen(
    uiState: AppUiState,
    journalItemsList: List<JournalItemDBModel>,
    onAddEntryClick: () -> Unit = {}
) {
    val spacing = MindMateTheme.spacing
    val latestMoodColor = if (journalItemsList.isNotEmpty()) {
        getMoodColor(journalItemsList.first().mood)
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
        Canvas(modifier = Modifier
            .fillMaxSize()
            .blur(100.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(animatedAuraColor.copy(alpha = 0.08f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.2f),
                    radius = size.width * 1.5f
                )
            )
        }

        Scaffold(
            topBar = { HomeScreenHeader(onAddEntryClick) },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                when (uiState) {
                    is AppUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    }
                    is AppUiState.Error -> {
                        Text(
                            text = uiState.message,
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else -> {
                        TimelineList(journalItemsList, onAddEntryClick)
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineList(
    items: List<JournalItemDBModel>,
    onAddEntryClick: () -> Unit
) {
    val timelineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawLine(
                    color = timelineColor,
                    start = Offset(32.dp.toPx(), 0f),
                    end = Offset(32.dp.toPx(), size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }
    ) {
        item {
            Spacer(modifier = Modifier.height(MindMateTheme.spacing.sm))
            DailyHookStateless(onAddEntryClick)
            Spacer(modifier = Modifier.height(MindMateTheme.spacing.lg))
        }

        if (items.isEmpty()) {
            item { EmptyTimelineStateStateless(onAddEntryClick) }
        } else {
            itemsIndexed(
                items = items,
                key = { _, item -> item.id }
            ) { _, item ->
                TimelineMomentStateless(item)
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun DailyHookStateless(onClick: () -> Unit) {
    val spacing = MindMateTheme.spacing
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.lg)
            .padding(start = spacing.xl)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .clickable { onClick() }
            .padding(spacing.lg)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "How are you today?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Tap to capture a moment",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
            Icon(
                Icons.Rounded.AutoAwesome,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TimelineMomentStateless(item: JournalItemDBModel) {
    val moodColor = getMoodColor(item.mood)
    val spacing = MindMateTheme.spacing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.md, vertical = spacing.sm)
    ) {
        Box(
            modifier = Modifier
                .width(spacing.xxl)
                .padding(top = spacing.md),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(moodColor.copy(alpha = 0.1f))
                    .border(1.dp, moodColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(getMoodEmoji(item.mood), fontSize = 12.sp)
            }
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { 20 },
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = spacing.md)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(spacing.lg)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UtilityMethods.formatMillisToTime(item.timeStamp),
                            style = MaterialTheme.typography.labelLarge,
                            color = moodColor.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.width(spacing.sm))
                        Box(
                            modifier = Modifier
                                .size(2.dp)
                                .background(
                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(spacing.sm))
                        Text(
                            text = UtilityMethods.formatDate(item.timeStamp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.height(spacing.sm))
                    Text(
                        text = item.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyTimelineStateStateless(onAddEntryClick: () -> Unit) {
    val spacing = MindMateTheme.spacing
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
        )
        Spacer(modifier = Modifier.height(spacing.lg))
        Text(
            text = "Your Journey Awaits",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Begin your path of reflection today. Every moment is worth remembering.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = spacing.sm)
        )
        Spacer(modifier = Modifier.height(spacing.xl))
        Button(
            onClick = onAddEntryClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text("Begin Today", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimelineScreenPreview() {
    MindMateTheme {
        TimelineScreen(
            uiState = AppUiState.Idle,
            journalItemsList = listOf(
                JournalItemDBModel(
                    "1",
                    "Found some peace.",
                    "Preview text",
                    MoodsEnum.HAPPY,
                    System.currentTimeMillis() - 86400000
                ),
                JournalItemDBModel(
                    "2",
                    "A bit stressed.",
                    "Preview text",
                    MoodsEnum.ANXIOUS,
                    System.currentTimeMillis() - 86400000
                )
            )
        )
    }
}
