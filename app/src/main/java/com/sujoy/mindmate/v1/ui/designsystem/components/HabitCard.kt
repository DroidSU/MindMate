package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme

@Composable
fun HabitCard(
    name: String,
    isCompleted: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.MindMateCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onToggle
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = if (isCompleted) Icons.Rounded.CheckCircle else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = if (isCompleted) "Completed" else "Not completed",
                    tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Large)
                )
            }
            Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isCompleted) FontWeight.Normal else FontWeight.Medium,
                color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
