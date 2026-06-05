package com.sujoy.mindmate.v1.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujoy.mindmate.v1.utils.ConstantsManager

@Entity(tableName = ConstantsManager.TABLE_JOURNAL_ITEMS)
data class JournalItemDBModel(
    @PrimaryKey val id: String,
    val title: String = "",
    val content: String,
    val mood: MoodsEnum,
    val timeStamp: Long,
    val tags: List<String> = emptyList(),
    val imageUrl: String? = null,
    val analyzedId: String = "",
    val sentimentScore: Float = 0f,
    val moodScore: Float? = null
)
