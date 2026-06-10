package com.sujoy.mindmate.v2.data.repositories

import com.sujoy.mindmate.v2.data.database.V2DatabaseDAO
import com.sujoy.mindmate.v2.data.models.MoodLog

class DBRepositoryImplV2(private val appDAO: V2DatabaseDAO) : DatabaseRepositoryV2 {
    override suspend fun insertMoodLog(moodLog: MoodLog) {
        appDAO.insertMoodLog(moodLog)
    }
}