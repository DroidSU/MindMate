package com.sujoy.mindmate.v1.data.repositories

import com.sujoy.mindmate.v1.data.models.JournalAnalyzedDbModel

interface MlModelRepository {

    suspend fun analyzeSentimentLocal(
        entryText: String,
        journalID: String
    ): Result<JournalAnalyzedDbModel>
}