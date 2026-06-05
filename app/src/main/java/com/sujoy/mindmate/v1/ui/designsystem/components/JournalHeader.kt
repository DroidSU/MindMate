package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.FilterList
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
fun JournalHeader(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Journal",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onFilterClick) {
            Icon(
                imageVector = Icons.Rounded.FilterList,
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onCalendarClick) {
            Icon(
                imageVector = Icons.Rounded.CalendarToday,
                contentDescription = "Calendar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
