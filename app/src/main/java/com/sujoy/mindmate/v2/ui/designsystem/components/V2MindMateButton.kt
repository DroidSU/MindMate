package com.sujoy.mindmate.v2.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme

@Composable
fun V2MindMateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val aiGradient = MindMateV2Theme.aiGradient

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(MindMateV2Theme.radius.Medium))
            .then(
                if (enabled) {
                    Modifier.background(aiGradient.brush)
                } else {
                    Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                }
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(
                horizontal = MindMateV2Theme.spacing.Large,
                vertical = MindMateV2Theme.spacing.Medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides if (enabled) Color.White else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                    content()
                }
            }
        }
    }
}

@Composable
fun V2MindMateSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(MindMateV2Theme.radius.Medium),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        contentPadding = PaddingValues(
            horizontal = MindMateV2Theme.spacing.Large,
            vertical = MindMateV2Theme.spacing.Medium
        ),
        content = content
    )
}
