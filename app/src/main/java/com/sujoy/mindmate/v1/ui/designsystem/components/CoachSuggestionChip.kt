package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@Composable
fun CoachSuggestionChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
            .clickable(onClick = onClick)
            .padding(
                horizontal = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium,
                vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small
            )
    ) {
        Icon(
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = null,
            modifier = Modifier.size(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
