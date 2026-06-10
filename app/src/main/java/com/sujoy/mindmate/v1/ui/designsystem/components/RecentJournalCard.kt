package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.v1.data.models.JournalItemDBModel
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecentJournalCard(
    journalItem: JournalItemDBModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(journalItem.timeStamp))

    _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MindMateCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mood Icon Box
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(
                        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodColor(
                            journalItem.mood
                        ).copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodEmoji(
                        journalItem.mood
                    ), style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Great Day", // In image it says Great Day, could be derived from content/mood
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = journalItem.content,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))

            // Thumbnail Illustration
            Box(
                modifier = Modifier
                    .size(80.dp, 60.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
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
        MoodsEnum.ENERGETIC -> Color(0xFFFFD700)
        MoodsEnum.HAPPY -> Color(0xFF7AB8FF)
        MoodsEnum.NEUTRAL -> Color(0xFF6CC8A1)
        MoodsEnum.SAD -> Color(0xFFFFA500)
        MoodsEnum.ANGRY -> Color(0xFFFF4D4D)
        else -> Color.Gray
    }
}
