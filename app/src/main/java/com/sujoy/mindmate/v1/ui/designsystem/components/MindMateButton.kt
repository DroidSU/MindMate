package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@Composable
fun MindMateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(
            horizontal = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Large,
            vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium
        ),
        content = content
    )
}

@Composable
fun MindMateSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        contentPadding = PaddingValues(
            horizontal = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Large,
            vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium
        ),
        content = content
    )
}
