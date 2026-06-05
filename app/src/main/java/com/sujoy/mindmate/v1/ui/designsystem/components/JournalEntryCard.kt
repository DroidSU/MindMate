package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalEntryCard(
    entry: JournalItemDBModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val timeString = timeFormat.format(Date(entry.timeStamp))

    val moodTitle =
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodTitle(entry.mood)

    _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MindMateCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodIndicator(mood = entry.mood)

            Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$timeString • $moodTitle",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }

                Text(
                    text = if (entry.title.isNotBlank()) entry.title else "Untitled Reflection",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = entry.content,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                if (entry.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
                    LazyRow(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                            4.dp
                        )
                    ) {
                        items(entry.tags) { tag ->
                            _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.JournalTagChip(
                                text = tag
                            )
                        }
                    }
                }
            }

            if (entry.imageUrl != null) {
                Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 64.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            } else {
                // Image placeholder from UI reference image shows it even if it might be default
                Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 64.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                )
            }
        }
    }
}

private fun getMoodTitle(mood: MoodsEnum): String {
    return when (mood) {
        MoodsEnum.ENERGETIC -> "Great Day"
        MoodsEnum.HAPPY -> "Good Day"
        MoodsEnum.NEUTRAL -> "Okay Day"
        MoodsEnum.SAD -> "Low Day"
        MoodsEnum.ANGRY -> "Awful Day"
        MoodsEnum.CALM -> "Calm Evening"
        else -> "Reflective Moment"
    }
}
