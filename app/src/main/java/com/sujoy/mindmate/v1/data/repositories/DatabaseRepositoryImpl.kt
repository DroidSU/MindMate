package com.sujoy.mindmate.v1.data.repositories

import com.sujoy.mindmate.v1.data.database.AppDAO
import com.sujoy.mindmate.v1.data.models.AverageMoodDBModel
import com.sujoy.mindmate.v1.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.v1.data.models.JournalItemDBModel
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(private val appDAO: AppDAO) :
    DatabaseRepository {
    override fun getJournalItems(): Flow<List<JournalItemDBModel>> {
        return appDAO.getAllJournals()
    }

    override fun getFirst10JournalItems(): Flow<List<JournalItemDBModel>> {
        return appDAO.getFirst10Journals()
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

    override suspend fun clearAllData() {
        appDAO.clearAllData()
    }

    override suspend fun getJournalsForDate(date: String): List<JournalItemDBModel> {
        return appDAO.getJournalsForDate(date)
    }

    override suspend fun insertAverageMood(averageMood: AverageMoodDBModel) {
        appDAO.insertAverageMood(averageMood)
    }

    override fun getAllAverageMoods(): Flow<List<AverageMoodDBModel>> {
        return appDAO.getAllAverageMoods()
    }
}
