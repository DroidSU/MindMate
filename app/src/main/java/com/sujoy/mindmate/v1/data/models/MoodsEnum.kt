package com.sujoy.mindmate.v1.data.models

enum class MoodsEnum {
    ANGRY, SAD, ANXIOUS, NEUTRAL, CALM, HAPPY, ENERGETIC
}

/**
 * Maps MoodsEnum to a numerical value for graphing.
 * Scale: -1.0 (Most Negative) to 1.0 (Most Positive)
 */
fun MoodsEnum.toGraphValue(): Float {
    return when (this) {
        MoodsEnum.HAPPY -> 1.0f
        MoodsEnum.ENERGETIC -> 0.8f
        MoodsEnum.CALM -> 0.5f
        MoodsEnum.NEUTRAL -> 0.0f
        MoodsEnum.ANXIOUS -> -0.3f
        MoodsEnum.SAD -> -0.8f
        MoodsEnum.ANGRY -> -1.0f
    }
}