package com.sujoy.mindmate.ui.views.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.ui.theme.DarkColorScheme
import com.sujoy.mindmate.ui.theme.LightColorScheme
import com.sujoy.mindmate.utils.ConstantsManager

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodSelector(onThemeChange: (ColorScheme) -> Unit) {
    val moods = listOf(
        "😊 ${ConstantsManager.HAPPY}",
        "💪 ${ConstantsManager.MOTIVATED}",
        "😐 ${ConstantsManager.NEUTRAL}",
        "😢 ${ConstantsManager.SAD}",
        "⚡️ ${ConstantsManager.ANXIOUS}",
        "🔥 ${ConstantsManager.ANGRY}",
        "😴 ${ConstantsManager.TIRED}",
        "😔 ${ConstantsManager.LONELY}",
        "😑 ${ConstantsManager.BORED}",
    )
    val isDarkTheme = isSystemInDarkTheme()
    var selectedMood by remember { mutableStateOf<String?>(ConstantsManager.HAPPY) }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Spacing between items on the same row
        verticalArrangement = Arrangement.spacedBy(12.dp),  // Spacing between rows
    ) {
        // Loop through the moods directly
        moods.forEach { mood ->
            SuggestionChip(
                onClick = {
                    selectedMood = mood
                    when {
//                        mood.contains(ConstantsManager.HAPPY, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) HappyDarkColorScheme else HappyLightColorScheme)
//                        }
//
//                        mood.contains(ConstantsManager.MOTIVATED, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) HappyDarkColorScheme else HappyLightColorScheme)
//                        }
//
////                        mood.contains(ConstantsManager.CALM) -> {
////                            onThemeChange(if (isDarkTheme) CalmDarkColorScheme else CalmLightColorScheme)
////                        }
//
//                        mood.contains(ConstantsManager.ANGRY, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) AngryDarkColorScheme else AngryLightColorScheme)
//                        }
//
//                        mood.contains(ConstantsManager.SAD, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) SadDarkColorScheme else SadLightColorScheme)
//                        }
//
//                        mood.contains(ConstantsManager.ANXIOUS, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) AnxiousDarkColorScheme else AnxiousLightColorScheme)
//                        }
//
//                        mood.contains(ConstantsManager.TIRED, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) TiredDarkColorScheme else TiredLightColorScheme)
//                        }
//
//                        mood.contains(ConstantsManager.BORED, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) BoredDarkColorScheme else BoredLightColorScheme)
//                        }
//
//                        mood.contains(ConstantsManager.LONELY, ignoreCase = true) -> {
//                            onThemeChange(if (isDarkTheme) LonelyDarkColorScheme else LonelyLightColorScheme)
//                        }

                        else -> {
                            onThemeChange(if (isDarkTheme) DarkColorScheme else LightColorScheme)
                        }
                    }
                },
                text = mood,
                isSelected = mood == selectedMood
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodSelectorPreview() {
    MoodSelector(onThemeChange = {})
}