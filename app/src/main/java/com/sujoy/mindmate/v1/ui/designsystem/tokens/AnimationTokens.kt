package com.sujoy.mindmate.v1.ui.designsystem.tokens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween

object AnimationTokens {
    val DefaultDuration = 300
    val FastDuration = 150
    val SlowDuration = 500

    val DefaultEasing = tween<Float>(durationMillis = DefaultDuration)

    val GentleSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )

    val MoodSelectionSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val CardFadeSpec = tween<Float>(durationMillis = DefaultDuration)

    val EntranceSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
}
