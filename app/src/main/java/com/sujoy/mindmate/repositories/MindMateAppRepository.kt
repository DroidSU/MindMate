package com.sujoy.mindmate.repositories

import com.sujoy.mindmate.models.AnalyzedMoodObject

interface MindMateAppRepository {
    suspend fun analyzeMood(entryText: String): Result<AnalyzedMoodObject>
}