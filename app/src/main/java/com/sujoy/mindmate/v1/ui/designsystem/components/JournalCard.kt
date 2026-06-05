package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalCard(
    journalItem: JournalItemDBModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(journalItem.timeStamp))

    _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MindMateCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodEmoji(
                    journalItem.mood
                ),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
            Column {
                Text(
                    text = journalItem.mood.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium))
        Text(
            text = journalItem.content,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun getMoodEmoji(mood: MoodsEnum): String {
    return when (mood) {
        MoodsEnum.ANGRY -> "😡"
        MoodsEnum.SAD -> "😢"
        MoodsEnum.ANXIOUS -> "😰"
        MoodsEnum.NEUTRAL -> "😐"
        MoodsEnum.CALM -> "😌"
        MoodsEnum.HAPPY -> "😊"
        MoodsEnum.ENERGETIC -> "🤩"
    }
}
