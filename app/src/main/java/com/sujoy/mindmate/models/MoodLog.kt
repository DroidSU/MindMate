package com.sujoy.mindmate.models

import androidx.compose.ui.graphics.Color

data class MoodLog(
    val mood: String,
    val emoji: String,
    val time: String,
    val journalEntry: String? = null,
    val color: Color
)
