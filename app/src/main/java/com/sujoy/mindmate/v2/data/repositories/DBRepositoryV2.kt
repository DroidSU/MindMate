package com.sujoy.mindmate.v2.data.repositories

import com.sujoy.mindmate.v2.data.models.MoodLog

interface DatabaseRepositoryV2 {
    suspend fun insertMoodLog(moodLog: MoodLog)
}