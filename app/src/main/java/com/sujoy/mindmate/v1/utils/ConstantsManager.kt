package com.sujoy.mindmate.v1.utils

class ConstantsManager {
    companion object {
        const val APP_TAG = "MindMate_Tag"
        const val Error_Tag = "MindMate_Error"
        const val Success_Tag = "MindMate_Success"
        const val GEN_MODEL_VERSION = "gemini-2.5-flash"

        const val HAPPY = "Happy"
        const val SAD = "Sad"
        const val ANGRY = "Angry"
        const val ANXIOUS = "Anxious"
        const val MOTIVATED = "Motivated"
        const val TIRED = "Tired"
        const val LONELY = "Lonely"
        const val BORED = "Bored"
        const val NEUTRAL = "Neutral"

        const val JOURNAL_DB_NAME_OLD = "journal_table"
        const val JOURNAL_ID = "id"
        const val JOURNAL_DB_VERSION = 1
        const val JOURNAL_DATE_OLD = "date"

        const val TABLE_JOURNAL_ITEMS = "journal_items"
        const val TABLE_JOURNAL_TIMESTAMP = "timestamp"
        const val TABLE_JOURNAL_ANALYZED = "journal_analyzed"
        const val SENTIMENT_SCORE = "sentimentScore"

        const val MOOD_SCORE = "moodScore"

        const val REMINDER_OPT_1 = "Only when mood is risky"
        const val REMINDER_OPT_2 = "Daily check-in + mood checks"
        const val REMINDER_OPT_3 = "Fixed Daily Time"


        // v2 constants
        const val APP_TAG_V2 = "MindMateV2"
    }
}