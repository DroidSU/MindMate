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
    val timestamp: Long,       // Time of the entry
    val dateString: String,    // Format: "YYYY-MM-DD"
    val mood: String,          // String representation of the MoodEnum
    val moodScore: Int,        // Integer scale: 1 (Very Low) to 5 (Very High)
    val emotionalTag: String?  // Optional (e.g., "Anxious", "Calm", "Tired")
)

