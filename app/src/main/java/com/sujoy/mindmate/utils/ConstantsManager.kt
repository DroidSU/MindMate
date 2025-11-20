package com.sujoy.mindmate.utils

class ConstantsManager {
    companion object {
        const val Error_Tag = "MindMate_Error"
        const val Success_Tag = "MindMate_Success"
        const val GEN_MODEL_VERSION = "gemini-2.5-flash"

        const val HAPPY = "HAPPY"
        const val SAD = "SAD"
        const val ANGRY = "ANGRY"
        const val ANXIOUS = "ANXIOUS"
        const val MOTIVATED = "MOTIVATED"
        const val NEUTRAL = "NEUTRAL"

        const val JOURNAL_DB_NAME = "journal_table"
        const val JOURNAL_ID = "id"
        const val JOURNAL_DB_VERSION = 1
        const val JOURNAL_DATE = "date"

        const val REMINDER_OPT_1 = "Only when mood is risky"
        const val REMINDER_OPT_2 = "Daily check-in + mood checks"
        const val REMINDER_OPT_3 = "Fixed Daily Time"
    }
}