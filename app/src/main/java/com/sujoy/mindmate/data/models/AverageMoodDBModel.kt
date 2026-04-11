package com.sujoy.mindmate.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "average_mood")
data class AverageMoodDBModel(
    @PrimaryKey val date: String, // Format: YYYY-MM-DD
    @ColumnInfo(name = "avg_mood_score") val avgMoodScore: Float
)
