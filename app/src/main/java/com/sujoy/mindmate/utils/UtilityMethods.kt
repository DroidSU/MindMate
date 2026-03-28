package com.sujoy.mindmate.utils

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
import com.sujoy.mindmate.ui.theme.MoodStressedDark
import com.sujoy.mindmate.ui.theme.MoodStressedLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        @Composable
        fun getMoodColor(mood: MoodsEnum): Color {
            val isDark = isSystemInDarkTheme()
            return when (mood) {
                MoodsEnum.HAPPY -> if (isDark) MoodHappyDark else MoodHappyLight
                MoodsEnum.SAD -> if (isDark) MoodSadDark else MoodSadLight
                MoodsEnum.ANGRY -> if (isDark) MoodAngryDark else MoodAngryLight
                MoodsEnum.STRESSED -> if (isDark) MoodStressedDark else MoodStressedLight
                MoodsEnum.ANXIOUS -> if (isDark) MoodAnxiousDark else MoodAnxiousLight
                MoodsEnum.RELAXED -> if (isDark) MoodRelaxedDark else MoodRelaxedLight
                MoodsEnum.MOTIVATED -> if (isDark) MoodMotivatedDark else MoodMotivatedLight
                MoodsEnum.NEUTRAL -> if (isDark) MoodNeutralDark else MoodNeutralLight
            }
        }

        fun getMoodEmoji(mood: MoodsEnum): String {
            return when (mood) {
                MoodsEnum.HAPPY -> "😊"
                MoodsEnum.SAD -> "😔"
                MoodsEnum.ANGRY -> "😠"
                MoodsEnum.STRESSED -> "😫"
                MoodsEnum.ANXIOUS -> "😟"
                MoodsEnum.RELAXED -> "😌"
                MoodsEnum.MOTIVATED -> "💪"
                MoodsEnum.NEUTRAL -> "😐"
            }
        }
    }
}
