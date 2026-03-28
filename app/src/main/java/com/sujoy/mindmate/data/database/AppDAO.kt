package com.sujoy.mindmate.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.utils.ConstantsManager
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalItemDBModel)

    @Query("SELECT * FROM " + ConstantsManager.TABLE_JOURNAL_ITEMS + " ORDER BY " + ConstantsManager.TABLE_JOURNAL_TIMESTAMP + " DESC")
    fun getAllJournals(): Flow<List<JournalItemDBModel>>

}