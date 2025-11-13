package com.sujoy.mindmate.repositories

import com.sujoy.mindmate.db.JournalDAO
import com.sujoy.mindmate.models.JournalItemModel

class DatabaseRepositoryImpl(private val journalDAO: JournalDAO) : DatabaseRepository {
    override suspend fun saveJournal(journal: JournalItemModel) {
        journalDAO.insertJournal(journal)
    }

    override suspend fun deleteJournal(id: String) {
        journalDAO.deleteJournalById(id)
    }
}