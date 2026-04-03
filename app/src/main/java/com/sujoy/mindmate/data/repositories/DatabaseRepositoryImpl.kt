package com.sujoy.mindmate.data.repositories

import com.sujoy.mindmate.data.database.AppDAO
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(private val appDAO: AppDAO) :
    DatabaseRepository {
    override fun getJournalItems(): Flow<List<JournalItemDBModel>> {
        return appDAO.getAllJournals()
    }

    override suspend fun saveJournalItem(item: JournalItemDBModel) {
        appDAO.insertJournal(item)
    }

    override suspend fun updateSentimentScore(id: String, score: Float) {
        appDAO.updateSentimentAndAnalysisId(id, score, "")
    }

    override suspend fun saveAnalysisItem(item: JournalAnalyzedDbModel) {
        appDAO.insertAnalysisItem(item)
    }

    override suspend fun saveAnalysisAndUpdateScore(item: JournalAnalyzedDbModel) {
        appDAO.saveAnalysisAndUpdateScore(item)
    }

    override suspend fun deleteJournal(id: String) {
        appDAO.deleteJournalReference(id)
    }
}
