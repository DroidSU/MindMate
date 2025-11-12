package com.sujoy.mindmate.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sujoy.mindmate.models.JournalItemModel
import com.sujoy.mindmate.utils.ConstantsManager
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalItemModel)

    @Query("SELECT * FROM " + ConstantsManager.JOURNAL_DB_NAME + " ORDER BY " + ConstantsManager.JOURNAL_DATE + " DESC")
    fun getAllJournals(): Flow<List<JournalItemModel>>
}
