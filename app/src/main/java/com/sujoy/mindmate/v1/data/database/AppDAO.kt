package com.sujoy.mindmate.v1.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sujoy.mindmate.data.models.AverageMoodDBModel
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.v1.utils.ConstantsManager
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalItemDBModel)

    @Query("SELECT * FROM " + ConstantsManager.TABLE_JOURNAL_ITEMS + " ORDER BY " + ConstantsManager.TABLE_JOURNAL_TIMESTAMP + " DESC")
    fun getAllJournals(): Flow<List<JournalItemDBModel>>

    @Query("SELECT * FROM " + ConstantsManager.TABLE_JOURNAL_ITEMS + " ORDER BY " + ConstantsManager.TABLE_JOURNAL_TIMESTAMP + " DESC LIMIT 10")
    fun getFirst10Journals(): Flow<List<JournalItemDBModel>>

    @Query("UPDATE " + ConstantsManager.TABLE_JOURNAL_ITEMS + " SET " + ConstantsManager.SENTIMENT_SCORE + " = :score, analyzedId = :analysisId WHERE " + ConstantsManager.JOURNAL_ID + " = :id")
    suspend fun updateSentimentAndAnalysisId(id: String, score: Float, analysisId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysisItem(item: JournalAnalyzedDbModel)

    @Transaction
    suspend fun saveAnalysisAndUpdateScore(item: JournalAnalyzedDbModel) {
        insertAnalysisItem(item)
        updateSentimentAndAnalysisId(item.journalId, item.sentimentScore, item.id)
    }

    @Query("DELETE FROM " + ConstantsManager.TABLE_JOURNAL_ITEMS + " WHERE " + ConstantsManager.JOURNAL_ID + " = :id")
    suspend fun deleteJournal(id: String)

    @Query("DELETE FROM " + ConstantsManager.TABLE_JOURNAL_ANALYZED + " WHERE " + ConstantsManager.JOURNAL_ID + " = :id")
    suspend fun deleteAnalysis(id: String)

    @Transaction
    suspend fun deleteJournalReference(id: String) {
        deleteJournal(id)
        deleteAnalysis(id)
    }

    @Query("DELETE FROM " + ConstantsManager.TABLE_JOURNAL_ITEMS)
    suspend fun deleteAllJournals()

    @Query("DELETE FROM " + ConstantsManager.TABLE_JOURNAL_ANALYZED)
    suspend fun deleteAllAnalysis()

    @Transaction
    suspend fun clearAllData() {
        deleteAllJournals()
        deleteAllAnalysis()
        clearAverageMoods()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAverageMood(averageMood: AverageMoodDBModel)

    @Query("SELECT * FROM journal_items WHERE date(timestamp / 1000, 'unixepoch', 'localtime') = :date")
    suspend fun getJournalsForDate(date: String): List<JournalItemDBModel>

    @Query("SELECT * FROM average_mood ORDER BY date DESC")
    fun getAllAverageMoods(): Flow<List<AverageMoodDBModel>>

    @Query("DELETE FROM average_mood")
    suspend fun clearAverageMoods()
}
