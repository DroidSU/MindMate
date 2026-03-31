package com.sujoy.mindmate.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujoy.mindmate.utils.ConstantsManager

@Entity(tableName = ConstantsManager.TABLE_JOURNAL_ITEMS)
data class JournalItemDBModel(
    @PrimaryKey val id: String,
    val content: String,
    val mood: MoodsEnum,
    val timeStamp: Long,
    val analyzedId: String = "",
    val sentimentScore: Float = 0f
)
