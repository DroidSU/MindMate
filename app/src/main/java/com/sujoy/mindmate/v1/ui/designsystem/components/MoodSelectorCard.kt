package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@Composable
fun MoodSelectorCard(
    selectedMood: MoodsEnum?,
    onMoodSelected: (MoodsEnum) -> Unit,
    onAddNoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MindMateCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "How are you feeling today?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onAddNoteClick) {
                    Text("Add Note", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Rounded.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodOption(
                    MoodsEnum.ENERGETIC,
                    "Great",
                    selectedMood == MoodsEnum.ENERGETIC
                ) { onMoodSelected(MoodsEnum.ENERGETIC) }
                _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodOption(
                    MoodsEnum.HAPPY,
                    "Good",
                    selectedMood == MoodsEnum.HAPPY
                ) { onMoodSelected(MoodsEnum.HAPPY) }
                _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodOption(
                    MoodsEnum.NEUTRAL,
                    "Okay",
                    selectedMood == MoodsEnum.NEUTRAL
                ) { onMoodSelected(MoodsEnum.NEUTRAL) }
                _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodOption(
                    MoodsEnum.SAD,
                    "Low",
                    selectedMood == MoodsEnum.SAD
                ) { onMoodSelected(MoodsEnum.SAD) }
                _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodOption(
                    MoodsEnum.ANGRY,
                    "Awful",
                    selectedMood == MoodsEnum.ANGRY
                ) { onMoodSelected(MoodsEnum.ANGRY) }
            }

            Spacer(modifier = Modifier.height(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Large))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
                Text(
                    text = "Your check-in helps MindMate understand you better.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MoodOption(
    mood: MoodsEnum,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.ExtraSmall)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodColor(
                        mood
                    ).copy(alpha = 0.15f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodEmoji(
                    mood
                ), style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun getMoodEmoji(mood: MoodsEnum): String {
    return when (mood) {
        MoodsEnum.ENERGETIC -> "😊"
        MoodsEnum.HAPPY -> "🙂"
        MoodsEnum.NEUTRAL -> "😐"
        MoodsEnum.SAD -> "😟"
        MoodsEnum.ANGRY -> "😫"
        else -> "❓"
    }
}

private fun getMoodColor(mood: MoodsEnum): Color {
    return when (mood) {
        MoodsEnum.ENERGETIC -> Color(0xFFFFD700) // Yellow
        MoodsEnum.HAPPY -> Color(0xFF7AB8FF) // Blue
        MoodsEnum.NEUTRAL -> Color(0xFF6CC8A1) // Green
        MoodsEnum.SAD -> Color(0xFFFFA500) // Orange
        MoodsEnum.ANGRY -> Color(0xFFFF4D4D) // Red
        else -> Color.Gray
    }
}
