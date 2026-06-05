package com.sujoy.mindmate.v1.data.repositories

import com.sujoy.mindmate.data.models.AverageMoodDBModel
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getJournalItems(): Flow<List<JournalItemDBModel>>
    fun getFirst10JournalItems(): Flow<List<JournalItemDBModel>>
    suspend fun saveJournalItem(item: JournalItemDBModel)

    suspend fun updateSentimentScore(id: String, score: Float)

    suspend fun saveAnalysisItem(item: JournalAnalyzedDbModel)
    suspend fun saveAnalysisAndUpdateScore(item: JournalAnalyzedDbModel)

    suspend fun deleteJournal(id: String)

    suspend fun clearAllData()

    suspend fun getJournalsForDate(date: String): List<JournalItemDBModel>
    suspend fun insertAverageMood(averageMood: AverageMoodDBModel)
    fun getAllAverageMoods(): Flow<List<AverageMoodDBModel>>
}
