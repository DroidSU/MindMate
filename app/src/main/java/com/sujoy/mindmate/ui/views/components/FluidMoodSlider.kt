package com.sujoy.mindmate.ui.views.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodColor
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodEmoji
import kotlin.math.sin

@Composable
fun FluidMoodSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onMoodDetected: (MoodsEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    // Map the 0-1 value to our MoodsEnum
    val moods = listOf(
        MoodsEnum.SAD,
        MoodsEnum.ANGRY,
        MoodsEnum.ANXIOUS,
        MoodsEnum.STRESSED,
        MoodsEnum.NEUTRAL,
        MoodsEnum.RELAXED,
        MoodsEnum.MOTIVATED,
        MoodsEnum.HAPPY
    )

    val currentMoodIndex = (value * (moods.size - 1)).toInt().coerceIn(0, moods.size - 1)
    val currentMood = moods[currentMoodIndex]

    LaunchedEffect(currentMood) {
        onMoodDetected(currentMood)
    }

    val moodColor = getMoodColor(currentMood)
    val animatedColor by animateColorAsState(targetValue = moodColor, label = "fluidColor")

    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveOffset"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onValueChange((offset.x / size.width).coerceIn(0f, 1f))
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onValueChange((change.position.x / size.width).coerceIn(0f, 1f))
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val fillWidth = width * value

            val path = Path().apply {
                moveTo(0f, height)
                lineTo(0f, height * 0.5f)

                val waveAmplitude = 15f
                val waveFrequency = 0.05f
                for (x in 0..fillWidth.toInt()) {
                    val y = height * 0.5f + waveAmplitude * sin(x * waveFrequency + waveOffset)
                    lineTo(x.toFloat(), y)
                }

                lineTo(fillWidth, height)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        animatedColor.copy(alpha = 0.8f),
                        animatedColor.copy(alpha = 0.4f)
                    )
                )
            )

            val secondPath = Path().apply {
                moveTo(0f, height)
                lineTo(0f, height * 0.55f)
                val waveAmplitude = 10f
                val waveFrequency = 0.04f
                for (x in 0..fillWidth.toInt()) {
                    val y =
                        height * 0.55f + waveAmplitude * sin(x * waveFrequency - waveOffset * 0.8f)
                    lineTo(x.toFloat(), y)
                }
                lineTo(fillWidth, height)
                close()
            }
            drawPath(
                path = secondPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        animatedColor.copy(alpha = 0.4f),
                        animatedColor.copy(alpha = 0.1f)
                    )
                )
            )
        }

        // Floating Emoji and Label
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getMoodEmoji(currentMood),
                fontSize = 44.sp
            )
            Text(
                text = currentMood.name.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (value > 0.4f) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${(value * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = if (value > 0.4f) Color.White.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
