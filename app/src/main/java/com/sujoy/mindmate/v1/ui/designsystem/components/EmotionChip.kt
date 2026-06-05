package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@Composable
fun EmotionChip(
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
    contentColor: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = contentColor,
        modifier = modifier
            .clip(CircleShape)
            .background(containerColor)
            .padding(
                horizontal = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium,
                vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.ExtraSmall
            )
    )
}
