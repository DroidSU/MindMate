package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JournalTagChip(
    text: String,
    modifier: Modifier = Modifier
) {
    val tagColor =
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getTagColor(text)

    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontSize = 10.sp,
        color = tagColor,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(tagColor.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

private fun getTagColor(tag: String): Color {
    return when (tag.lowercase()) {
        "health", "wellness" -> Color(0xFF6CC8A1)
        "exercise", "fitness" -> Color(0xFF7AB8FF)
        "gratitude", "family" -> Color(0xFFF7C76B)
        "work" -> Color(0xFF9D7CFF)
        "mindfulness", "meditation" -> Color(0xFF6C7CFF)
        "mental health", "reflection" -> Color(0xFFFF9D8F)
        else -> Color(0xFF6C7CFF)
    }
}
