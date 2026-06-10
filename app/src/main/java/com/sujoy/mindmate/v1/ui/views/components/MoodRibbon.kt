package com.sujoy.mindmate.v1.ui.views.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import com.sujoy.mindmate.v1.utils.UtilityMethods.Companion.getMoodColor
import com.sujoy.mindmate.v1.utils.UtilityMethods.Companion.getMoodEmoji

@Composable
fun MoodRibbon(
    selectedMood: MoodsEnum,
    onMoodSelected: (MoodsEnum) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(MoodsEnum.entries) { mood ->
            val isSelected = mood == selectedMood
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.3f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "scale"
            )

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) getMoodColor(mood).copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                    .clickable { onMoodSelected(mood) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getMoodEmoji(mood),
                    fontSize = if (isSelected) 34.sp else 28.sp
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 6.dp)
                            .size(4.dp)
                            .background(getMoodColor(mood), CircleShape)
                    )
                }
            }
        }
    }
}
