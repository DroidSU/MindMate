package com.sujoy.mindmate.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.utils.ConstantsManager
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalItemDBModel)

    @Query("SELECT * FROM " + ConstantsManager.TABLE_JOURNAL_ITEMS + " ORDER BY " + ConstantsManager.TABLE_JOURNAL_TIMESTAMP + " DESC")
    fun getAllJournals(): Flow<List<JournalItemDBModel>>

    @Query("UPDATE " + ConstantsManager.TABLE_JOURNAL_ITEMS + " SET " + ConstantsManager.SENTIMENT_SCORE + " = :score, analyzedId = :analysisId WHERE " + ConstantsManager.JOURNAL_ID + " = :id")
    suspend fun updateSentimentAndAnalysisId(id: String, score: Float, analysisId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysisItem(item: JournalAnalyzedDbModel)

    @Transaction
    suspend fun saveAnalysisAndUpdateScore(item: JournalAnalyzedDbModel) {
        insertAnalysisItem(item)
        updateSentimentAndAnalysisId(item.journalId, item.sentimentScore, item.id)
    }
}
