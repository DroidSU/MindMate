package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.v1.data.models.MoodsEnum

@Composable
fun MoodIndicator(
    mood: MoodsEnum,
    modifier: Modifier = Modifier
) {
    val moodColor =
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodColor(mood)

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(moodColor.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.getMoodEmoji(
                mood
            ),
            style = MaterialTheme.typography.headlineSmall
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
        else -> "😊"
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
