package com.sujoy.mindmate.ui.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ModernActionButton(
    text: String,
    isEnabled: Boolean,
    moodColor: Color,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) {
    // Use the primary theme color for better contrast and a more "attractive" look
    // This uses md_theme_light_primary (Deep Indigo) or dark variant
    val actionColor = MaterialTheme.colorScheme.primary

    // We use the moodColor for the "glow" to keep it connected to the UI state
    val glowColor = moodColor.copy(alpha = 0.7f)

    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .height(58.dp)
            .width(160.dp)
            .shadow(
                elevation = if (isEnabled) 16.dp else 0.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = glowColor
            ),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = contentColor,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .height(58.dp)
                .width(160.dp)
                .background(
                    brush = if (isEnabled) {
                        Brush.verticalGradient(
                            colors = listOf(
                                actionColor.copy(alpha = 0.85f),
                                actionColor
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        )
                    },
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 1.dp,
                    brush = if (isEnabled) {
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(Color.Transparent, Color.Transparent)
                        )
                    },
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = if (isEnabled) Color.White else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.2f
                )
            )
        }
    }
}
