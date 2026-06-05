package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@Composable
fun MoodSelector(
    selectedMood: MoodsEnum?,
    onMoodSelected: (MoodsEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium)
    ) {
        items(MoodsEnum.entries) { mood ->
            _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MoodItem(
                mood = mood,
                isSelected = mood == selectedMood,
                onClick = { onMoodSelected(mood) }
            )
        }
    }
}

@Composable
private fun MoodItem(
    mood: MoodsEnum,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val emoji = when (mood) {
        MoodsEnum.ANGRY -> "😡"
        MoodsEnum.SAD -> "😢"
        MoodsEnum.ANXIOUS -> "😰"
        MoodsEnum.NEUTRAL -> "😐"
        MoodsEnum.CALM -> "😌"
        MoodsEnum.HAPPY -> "😊"
        MoodsEnum.ENERGETIC -> "🤩"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small)
    ) {
        Text(
            text = emoji,
            fontSize = if (isSelected) 32.sp else 24.sp,
            modifier = Modifier.padding(bottom = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.ExtraSmall)
        )
        Text(
            text = mood.name.lowercase().replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
