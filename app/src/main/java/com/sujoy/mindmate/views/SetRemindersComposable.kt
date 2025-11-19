package com.sujoy.mindmate.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.EventRepeat
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.ui.theme.LocalGradientColors
import com.sujoy.mindmate.ui.theme.MindMateTheme

@Composable
fun SetReminder(onContinue: () -> Unit) {
    var selectedOption by remember { mutableStateOf("Daily check-in + mood checks") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "When do you want preventive nudges?",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.get_reminders_for_your_mood_patterns),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 18.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ReminderOption(
                icon = Icons.Outlined.ReportProblem,
                iconColor = Color(0xFFE57373), // A softer red
                title = "Risky Moods Only",
                subtitle = "Nudges for risky patterns",
                isSelected = selectedOption == "Only when mood is risky",
                onClick = { selectedOption = "Only when mood is risky" }
            )
            ReminderOption(
                icon = Icons.Outlined.EventRepeat,
                iconColor = Color(0xFF81C784), // A gentle green
                title = "Daily + Mood Checks",
                subtitle = "Check-in and pattern nudges (Recommended)",
                isSelected = selectedOption == "Daily check-in + mood checks",
                onClick = { selectedOption = "Daily check-in + mood checks" }
            )
            ReminderOption(
                icon = Icons.Outlined.Schedule,
                iconColor = Color(0xFF64B5F6), // A calming blue
                title = "Fixed Daily Time",
                subtitle = "Choose a specific time for your reminder",
                isSelected = selectedOption == "At a fixed time daily",
                onClick = {
                    selectedOption = "At a fixed time daily"
                    // TODO: Open time picker
                }
            )
        }


        if (selectedOption == "At a fixed time daily") {
            // Placeholder for time picker.
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Time picker should be displayed here.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(start = 28.dp, end = 28.dp, bottom = 10.dp)
                .shadow(10.dp, RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LocalGradientColors.current.buttonStart,
                            LocalGradientColors.current.buttonEnd
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable(onClick = {})
        ) {
            Text(
                stringResource(R.string.finish_and_create_baseline),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp)
            )
        }

        Text(
            text = "You can change these later.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

@Composable
private fun ReminderOption(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(
            alpha = 0.5f
        )
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconColor)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isSelected) {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SetReminderPreview() {
    MindMateTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
        ) {
            SetReminder(onContinue = {})
        }
    }
}
