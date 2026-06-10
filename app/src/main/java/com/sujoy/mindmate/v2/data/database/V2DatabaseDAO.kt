package com.sujoy.mindmate.v2.data.database

import androidx.room.Insert
import androidx.room.Query
import com.sujoy.mindmate.v2.data.models.MoodLog

interface V2DatabaseDAO {

    @Insert
    suspend fun insertMoodLog(moodLog: MoodLog)

    @Query("SELECT * FROM mood_logs WHERE dateString = :date")
    suspend fun getMoodLogsByDate(date: String): List<MoodLog>
}