package com.sujoy.mindmate.data.repositories

import com.sujoy.mindmate.data.database.AppDAO
import com.sujoy.mindmate.data.database.JournalDAO
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.JournalItemModel
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(private val journalDAO: JournalDAO, private val appDAO: AppDAO) :
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

    override suspend fun saveJournal(journal: JournalItemModel) {
        journalDAO.insertJournal(journal)
    }

    override suspend fun deleteJournal(id: String) {
        journalDAO.deleteJournalById(id)
    }
}
