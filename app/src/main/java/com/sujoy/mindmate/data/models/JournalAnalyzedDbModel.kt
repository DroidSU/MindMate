package com.sujoy.mindmate.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujoy.mindmate.utils.ConstantsManager


@Entity(tableName = ConstantsManager.TABLE_JOURNAL_ANALYZED)
data class JournalAnalyzedDbModel(
    @PrimaryKey val id: String,
    val journalId: String,
    val sentimentScore: Float,
    val mood: String,
    val message: String,
    val timeStamp: Long,
)
