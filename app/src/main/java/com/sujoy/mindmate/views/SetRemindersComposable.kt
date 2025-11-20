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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.EventRepeat
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.R
import com.sujoy.mindmate.ui.theme.LocalGradientColors
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.utils.ConstantsManager
import com.sujoy.mindmate.utils.UtilityMethods
import com.sujoy.mindmate.vm.OnboardingViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetReminder(onContinue: () -> Unit, onboardingViewModel: OnboardingViewModel? = viewModel()) {
    val selectedOption by onboardingViewModel?.reminderOption?.collectAsState() ?: remember {
        mutableStateOf(
            ConstantsManager.REMINDER_OPT_2
        )
    }

    val selectedTime by onboardingViewModel?.reminderTime?.collectAsState() ?: remember {
        mutableStateOf(
            Calendar.getInstance().timeInMillis
        )
    }


    var showTimePicker by remember { mutableStateOf(false) }
    val timeCalendar = Calendar.getInstance().apply { timeInMillis = selectedTime }
    val timePickerState = rememberTimePickerState(
        initialHour = timeCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = timeCalendar.get(Calendar.MINUTE)
    )


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
                isSelected = selectedOption == ConstantsManager.REMINDER_OPT_1,
                onClick = { onboardingViewModel?.setReminderOption(ConstantsManager.REMINDER_OPT_1) }
            )
            ReminderOption(
                icon = Icons.Outlined.EventRepeat,
                iconColor = Color(0xFF81C784), // A gentle green
                title = "Daily + Mood Checks",
                subtitle = "Check-in and pattern nudges (Recommended)",
                isSelected = selectedOption == ConstantsManager.REMINDER_OPT_2,
                onClick = { onboardingViewModel?.setReminderOption(ConstantsManager.REMINDER_OPT_2) }
            )
            ReminderOption(
                icon = Icons.Outlined.Schedule,
                iconColor = Color(0xFF64B5F6), // A calming blue
                title = "Fixed Daily Time",
                subtitle = "Choose a specific time for your reminder",
                isSelected = selectedOption == ConstantsManager.REMINDER_OPT_3,
                onClick = {
                    showTimePicker = true
                    onboardingViewModel?.setReminderOption(ConstantsManager.REMINDER_OPT_3)
                }
            )
        }


        if (selectedOption == ConstantsManager.REMINDER_OPT_3) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Selected Time: ${UtilityMethods.formatMillisToTime(selectedTime)}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )


            if (showTimePicker) {
                CustomTimePickerDialog(
                    onDismissRequest = { showTimePicker = false },
                    onConfirm = {
                        val cal = Calendar.getInstance()
                        cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        cal.set(Calendar.MINUTE, timePickerState.minute)
                        cal.isLenient = false
                        onboardingViewModel?.setReminderTime(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        showTimePicker = false
                    },
                ) {
                    TimePicker(state = timePickerState)
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "You can change these later.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .shadow(12.dp, CircleShape)
                .clip(CircleShape)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LocalGradientColors.current.buttonStart,
                            LocalGradientColors.current.buttonEnd
                        )
                    )
                )
                .clickable(onClick = onContinue)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next Step: Summary",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun CustomTimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismissRequest) { Text("Cancel") }
                    TextButton(onClick = onConfirm) { Text("OK") }
                }
            }
        }
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
            SetReminder(onContinue = {}, onboardingViewModel = null)
        }
    }
}
