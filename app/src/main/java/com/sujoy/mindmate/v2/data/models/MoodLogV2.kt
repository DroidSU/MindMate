package com.sujoy.mindmate.v2.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mood_logs",
    indices = [Index(value = ["dateString"])]
)
data class MoodLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val dateString: String,
    val mood: String,          // Mood string (e.g., "Happy", "Sad", "Angry") from MoodsEnumV2
    val moodScore: Int,        // Integer scale: 1 (Very Low) to 5 (Very High)
    val emotionalTag: String?  // Optional (e.g., "Anxious", "Calm", "Tired")
)

