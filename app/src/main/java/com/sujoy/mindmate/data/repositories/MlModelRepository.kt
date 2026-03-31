package com.sujoy.mindmate.data.repositories

import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel

interface MlModelRepository {

    suspend fun analyzeSentimentLocal(
        entryText: String,
        journalID: String
    ): Result<JournalAnalyzedDbModel>
}