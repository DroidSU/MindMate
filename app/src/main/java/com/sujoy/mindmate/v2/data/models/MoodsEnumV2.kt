package com.sujoy.mindmate.v2.data.models

import com.sujoy.mindmate.R

data class MoodV2(
    val id: Int,
    val moodString: String,
    val iconResId: Int,
    val graphValue: Int
)

object MoodProviderV2 {
    val moods = listOf(
        MoodV2(1, "Great", R.drawable.ic_happy, 5),
        MoodV2(2, "Good", R.drawable.ic_good, 4),
        MoodV2(3, "Okay", R.drawable.ic_neutral, 3),
        MoodV2(4, "Low", R.drawable.emoji_sad, 2),
        MoodV2(5, "Awful", R.drawable.ic_angry, 1)
    )

    fun getMoodById(id: Int): MoodV2? = moods.find { it.id == id }
}
