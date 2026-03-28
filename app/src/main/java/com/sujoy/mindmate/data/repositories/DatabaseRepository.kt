package com.sujoy.mindmate.data.repositories

import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.models.JournalItemModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getJournalItems(): Flow<List<JournalItemDBModel>>
    suspend fun saveJournalItem(item: JournalItemDBModel)

    suspend fun saveJournal(journal: JournalItemModel)
    suspend fun deleteJournal(id: String)
}