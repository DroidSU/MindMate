package com.sujoy.mindmate.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujoy.mindmate.utils.ConstantsManager
import java.util.UUID

@Entity(tableName = ConstantsManager.JOURNAL_DB_NAME)
data class JournalItemModel(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var title: String,
    val body: String,
    val date: Long,
    val sentiment: String
)
