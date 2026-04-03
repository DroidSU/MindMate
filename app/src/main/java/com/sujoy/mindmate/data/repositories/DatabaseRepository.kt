package com.sujoy.mindmate.data.repositories

import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getJournalItems(): Flow<List<JournalItemDBModel>>
    suspend fun saveJournalItem(item: JournalItemDBModel)

    suspend fun updateSentimentScore(id: String, score: Float)

    suspend fun saveAnalysisItem(item: JournalAnalyzedDbModel)
    suspend fun saveAnalysisAndUpdateScore(item: JournalAnalyzedDbModel)

    suspend fun deleteJournal(id: String)
}
