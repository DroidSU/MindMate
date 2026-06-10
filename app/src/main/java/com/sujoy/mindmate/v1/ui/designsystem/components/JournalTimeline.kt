package com.sujoy.mindmate.v1.ui.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.v1.data.models.JournalItemDBModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalTimeline(
    entries: List<JournalItemDBModel>,
    modifier: Modifier = Modifier
) {
    val groupedEntries = entries.groupBy {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date(it.timeStamp))
    }

    Column(modifier = modifier.fillMaxWidth()) {
        groupedEntries.forEach { (month, monthEntries) ->
            Text(
                text = month,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium)
            )

            monthEntries.forEach { entry ->
                _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.TimelineEntryRow(
                    entry = entry
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Medium))
            }
        }
    }
}

@Composable
fun TimelineEntryRow(entry: JournalItemDBModel) {
    val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(Date(entry.timeStamp))
    val dateNumber = SimpleDateFormat("d", Locale.getDefault()).format(Date(entry.timeStamp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.TimelineDateMarker(
            dayOfWeek = dayOfWeek,
            dateNumber = dateNumber,
            modifier = Modifier
                .width(48.dp)
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.width(_root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme.spacing.Small))
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.components.JournalEntryCard(
            entry = entry,
            onClick = { /* TODO: Open entry */ }
        )
    }
}
