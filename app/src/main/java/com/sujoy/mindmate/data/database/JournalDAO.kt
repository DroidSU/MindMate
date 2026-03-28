package com.sujoy.mindmate.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sujoy.mindmate.data.models.JournalItemModel
import com.sujoy.mindmate.utils.ConstantsManager
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalItemModel)

    @Query("SELECT * FROM " + ConstantsManager.JOURNAL_DB_NAME_OLD + " ORDER BY " + ConstantsManager.JOURNAL_DATE_OLD + " DESC")
    fun getAllJournals(): Flow<List<JournalItemModel>>

    @Query("DELETE FROM " + ConstantsManager.JOURNAL_DB_NAME_OLD + " WHERE " + ConstantsManager.JOURNAL_ID + " = :journalId")
    suspend fun deleteJournalById(journalId: String)
}
