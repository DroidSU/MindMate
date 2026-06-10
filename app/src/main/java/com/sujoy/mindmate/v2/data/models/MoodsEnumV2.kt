package com.sujoy.mindmate.v2.data.models

import com.sujoy.mindmate.R

data class MoodV2(
    val id: Int,
    val moodString: String,
    val iconResId: Int,
    val graphValue: Float
)

object MoodProviderV2 {
    val moods = listOf(
        MoodV2(1, "Great", R.drawable.ic_happy, 1.0f),
        MoodV2(2, "Good", R.drawable.ic_motivated, 0.7f),
        MoodV2(3, "Okay", R.drawable.ic_neutral, 0.0f),
        MoodV2(4, "Low", R.drawable.ic_sad, -0.7f),
        MoodV2(5, "Awful", R.drawable.ic_angry, -1.0f)
    )

    fun getMoodById(id: Int): MoodV2? = moods.find { it.id == id }
}
