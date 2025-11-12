package com.sujoy.mindmate.repositories

import com.sujoy.mindmate.models.AnalyzedMoodObject
import com.sujoy.mindmate.models.JournalItemModel

interface NewJournalRepository {
    suspend fun analyzeMood(entryText: String): Result<AnalyzedMoodObject>
    suspend fun saveJournal(journal: JournalItemModel)
}