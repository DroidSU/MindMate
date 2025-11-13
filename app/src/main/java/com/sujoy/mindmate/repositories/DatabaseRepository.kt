package com.sujoy.mindmate.repositories

import com.sujoy.mindmate.models.JournalItemModel

interface DatabaseRepository {
    suspend fun saveJournal(journal: JournalItemModel)
    suspend fun deleteJournal(id: String)
}