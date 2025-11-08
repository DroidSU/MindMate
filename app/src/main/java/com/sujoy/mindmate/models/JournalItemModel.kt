package com.sujoy.mindmate.models

data class JournalItemModel(
    var id: String,
    var title: String,
    val body: String,
    val date: Long,
    val sentiment: Moods
)
