package com.sujoy.mindmate.ui.views.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodColor
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodEmoji
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FluidMoodSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onMoodDetected: (MoodsEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    val moods = MoodsEnum.entries.toList()
    val currentMoodIndex = (value * (moods.size - 1)).toInt().coerceIn(0, moods.size - 1)
    val currentMood = moods[currentMoodIndex]

    LaunchedEffect(currentMood) {
        onMoodDetected(currentMood)
    }

    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "sliderValue"
    )

    val moodColor = getMoodColor(currentMood)
    val animatedMoodColor by animateColorAsState(
        targetValue = moodColor,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "moodColor"
    )

    // Pre-calculate colors since getMoodColor is a Composable
    val moodColors = moods.map { getMoodColor(it) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onValueChange(calculateValueFromOffset(offset, size))
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onValueChange(calculateValueFromOffset(change.position, size))
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Curve parameters
            val arcRadius = width * 0.45f
            val centerX = width / 2
            val centerY = height * 1.05f

            val startAngle = 210f
            val sweepAngle = 120f

            // Draw the curved cylinder sections
            val strokeWidth = 20.dp.toPx()

            moods.forEachIndexed { index, mood ->
                val sectionSweep = sweepAngle / moods.size
                val sectionStart = startAngle + (index * sectionSweep)
                val mColor = moodColors[index]

                drawArc(
                    color = mColor.copy(alpha = 0.8f),
                    startAngle = sectionStart,
                    sweepAngle = sectionSweep,
                    useCenter = false,
                    topLeft = Offset(centerX - arcRadius, centerY - arcRadius),
                    size = Size(arcRadius * 2, arcRadius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )

                // Labels (Emojis)
                val midAngle = sectionStart + sectionSweep / 2
                val angleRad = Math.toRadians(midAngle.toDouble())

                val labelRadius = arcRadius + 32.dp.toPx()

                val labelX = centerX + labelRadius * cos(angleRad).toFloat()
                val labelY = centerY + labelRadius * sin(angleRad).toFloat()

                // Draw Text (Emoji)
                rotate(degrees = midAngle + 90f, pivot = Offset(labelX, labelY)) {
                    drawContext.canvas.nativeCanvas.apply {
                        val paint = android.graphics.Paint().apply {
                            textSize = 22.sp.toPx()
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                        drawText(getMoodEmoji(mood), labelX, labelY, paint)
                    }
                }
            }

            // Draw the Handle (Glow Ball)
            val handleAngle = startAngle + (animatedValue * sweepAngle)
            val handleAngleRad = Math.toRadians(handleAngle.toDouble())
            val handleX = centerX + arcRadius * cos(handleAngleRad).toFloat()
            val handleY = centerY + arcRadius * sin(handleAngleRad).toFloat()

            // Outer glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(animatedMoodColor.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(handleX, handleY),
                    radius = 20.dp.toPx()
                ),
                radius = 20.dp.toPx(),
                center = Offset(handleX, handleY)
            )

            // Inner ball
            drawCircle(
                color = Color.White,
                radius = 8.dp.toPx(),
                center = Offset(handleX, handleY)
            )
            drawCircle(
                color = animatedMoodColor,
                radius = 6.dp.toPx(),
                center = Offset(handleX, handleY)
            )
        }
    }
}

private fun calculateValueFromOffset(offset: Offset, size: IntSize): Float {
    val width = size.width.toFloat()
    val height = size.height.toFloat()
    val centerX = width / 2
    val centerY = height * 1.05f

    val dx = offset.x - centerX
    val dy = offset.y - centerY

    var angle = Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())).toFloat()
    if (angle < 0) angle += 360f

    val startAngle = 210f
    val sweepAngle = 120f

    // Normalize angle to 0..sweepAngle
    var normalizedAngle = angle - startAngle
    if (normalizedAngle < -180) normalizedAngle += 360f
    if (normalizedAngle > 180) normalizedAngle -= 360f

    return (normalizedAngle / sweepAngle).coerceIn(0f, 1f)
}

private fun Color.toArgb(): Int {
    return (this.alpha * 255.0f + 0.5f).toInt() shl 24 or
            ((this.red * 255.0f + 0.5f).toInt() shl 16) or
            ((this.green * 255.0f + 0.5f).toInt() shl 8) or
            (this.blue * 255.0f + 0.5f).toInt()
}
