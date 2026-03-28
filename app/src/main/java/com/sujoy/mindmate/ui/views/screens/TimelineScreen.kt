package com.sujoy.mindmate.ui.views.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Create
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.utils.UtilityMethods
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodColor
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodEmoji

@Composable
fun TimelineScreen(
    uiState: AppUiState,
    journalItemsList: List<JournalItemDBModel>,
    onAddEntryClick: () -> Unit = {}
) {
    // Determine background aura color based on the latest entry's mood
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
                    colors = listOf(animatedAuraColor.copy(alpha = 0.12f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.2f),
                    radius = size.width * 1.2f
                )
            )
        }

        Scaffold(
            topBar = {
                TimelineHeader(onAddEntryClick)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (uiState) {
                    is AppUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is AppUiState.Error -> {
                        Text(
                            text = uiState.message,
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    else -> {
                        if (journalItemsList.isEmpty()) {
                            EmptyTimelineState(onAddEntryClick)
                        } else {
                            TimelineList(journalItemsList, onAddEntryClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineHeader(onAddEntryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Your Story",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = (-1).sp
                )
                Text(
                    text = "A path through your emotions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .clickable { onAddEntryClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Create,
                    contentDescription = "New Entry",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun TimelineList(
    items: List<JournalItemDBModel>,
    onAddEntryClick: () -> Unit
) {
    val timelineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                // Draws a softer, glowing timeline path
                drawLine(
                    color = timelineColor,
                    start = Offset(40.dp.toPx(), 0f),
                    end = Offset(40.dp.toPx(), size.height),
                    strokeWidth = 3.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp)
    ) {
        item {
            DailyHook(onAddEntryClick)
            Spacer(modifier = Modifier.height(24.dp))
        }

        itemsIndexed(
            items = items,
            key = { _, item -> item.id }
        ) { index, item ->
            TimelineMoment(item)
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }
}

@Composable
private fun DailyHook(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 56.dp, end = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "How are you today?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Tap to add a new chapter",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            Icon(
                Icons.Rounded.AutoAwesome,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun TimelineMoment(item: JournalItemDBModel) {
    val moodColor = getMoodColor(item.mood)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Milestone Glowing Orb
        Box(
            modifier = Modifier
                .width(48.dp)
                .padding(top = 12.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(moodColor.copy(alpha = 0.2f))
                    .border(2.dp, moodColor.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(getMoodEmoji(item.mood), fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Glass Moment Card
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { 30 },
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(moodColor.copy(alpha = 0.3f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UtilityMethods.formatMillisToTime(item.timeStamp),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Black,
                            color = moodColor
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = UtilityMethods.formatDate(item.timeStamp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = item.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 26.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mood Label Tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(moodColor.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = item.mood.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelSmall,
                            color = moodColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyTimelineState(onAddEntryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .blur(2.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your Journey Awaits",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Every great story starts with a single reflection. Start yours now.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 12.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onAddEntryClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text("Begin Today", fontWeight = FontWeight.Bold)
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
                    "Found some peace in nature during my walk.",
                    MoodsEnum.RELAXED,
                    System.currentTimeMillis()
                ),
                JournalItemDBModel(
                    "2",
                    "A bit stressed about the upcoming deadline.",
                    MoodsEnum.STRESSED,
                    System.currentTimeMillis() - 3600000
                )
            )
        )
    }
}
