package com.sujoy.mindmate.utils

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.ui.theme.MoodAngryDark
import com.sujoy.mindmate.ui.theme.MoodAngryLight
import com.sujoy.mindmate.ui.theme.MoodAnxiousDark
import com.sujoy.mindmate.ui.theme.MoodAnxiousLight
import com.sujoy.mindmate.ui.theme.MoodHappyDark
import com.sujoy.mindmate.ui.theme.MoodHappyLight
import com.sujoy.mindmate.ui.theme.MoodMotivatedDark
import com.sujoy.mindmate.ui.theme.MoodMotivatedLight
import com.sujoy.mindmate.ui.theme.MoodNeutralDark
import com.sujoy.mindmate.ui.theme.MoodNeutralLight
import com.sujoy.mindmate.ui.theme.MoodRelaxedDark
import com.sujoy.mindmate.ui.theme.MoodRelaxedLight
import com.sujoy.mindmate.ui.theme.MoodSadDark
import com.sujoy.mindmate.ui.theme.MoodSadLight
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class UtilityMethods {
    companion object {

        fun formatDate(milliseconds: Long): String {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return formatter.format(Date(milliseconds))
        }

        fun formatMillisToWeeks(milliseconds: Long): String {
            val formatter = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            return formatter.format(Date(milliseconds))
        }

        fun formatMillisToTime(milliseconds: Long): String {
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return formatter.format(Date(milliseconds))
        }

        fun generateUniqueJournalId(): String {
            val timestamp = System.currentTimeMillis()
            val randomId = UUID.randomUUID().toString().take(8)
            val combined = "${timestamp}user${randomId}j"
            return hashString(combined)
        }

        fun generateUniqueAnalysisId(): String {
            val timestamp = System.currentTimeMillis()
            val randomId = UUID.randomUUID().toString().take(8)
            val combined = "${timestamp}user${randomId}s"
            return hashString(combined)
        }

        /**
         * Generates a unique username by appending a short time-based suffix in Base36.
         * Example: User_k7v2p9
         */
        fun generateUniqueUsername(base: String = "User"): String {
            val suffix = System.nanoTime().toString(36).takeLast(6)
            return "${base}_$suffix"
        }

        private fun hashString(input: String): String {
            return MessageDigest.getInstance("SHA-256")
                .digest(input.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }

        @Composable
        fun getMoodColor(mood: MoodsEnum): Color {
            val isDark = isSystemInDarkTheme()
            return when (mood) {
                MoodsEnum.HAPPY -> if (isDark) MoodHappyDark else MoodHappyLight
                MoodsEnum.SAD -> if (isDark) MoodSadDark else MoodSadLight
                MoodsEnum.ANGRY -> if (isDark) MoodAngryDark else MoodAngryLight
                MoodsEnum.ANXIOUS -> if (isDark) MoodAnxiousDark else MoodAnxiousLight
                MoodsEnum.CALM -> if (isDark) MoodRelaxedDark else MoodRelaxedLight
                MoodsEnum.ENERGETIC -> if (isDark) MoodMotivatedDark else MoodMotivatedLight
                MoodsEnum.NEUTRAL -> if (isDark) MoodNeutralDark else MoodNeutralLight
            }
        }

        fun getMoodEmoji(mood: MoodsEnum): String {
            return when (mood) {
                MoodsEnum.HAPPY -> "😊"
                MoodsEnum.SAD -> "😔"
                MoodsEnum.ANGRY -> "😡"
                MoodsEnum.ANXIOUS -> "😟"
                MoodsEnum.CALM -> "😌"
                MoodsEnum.ENERGETIC -> "🔥"
                MoodsEnum.NEUTRAL -> "😐"
            }
        }


        fun loadVocab(context: Context): Map<String, Int> {
            return context.assets.open("vocab.txt").bufferedReader().useLines { lines ->
                lines.mapIndexed { index, s -> s to index }.toMap()
            }
        }

        fun getDayName(dayOfYear: Int): String {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_YEAR, dayOfYear)
            return SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time)
        }
    }
}
