package com.sujoy.mindmate.v2.ui.views.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun V2AuthVisuals(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "v2_auth_bg")

    val primaryColor = MaterialTheme.colorScheme.primary
    val aiAccent = MindMateV2Theme.aiGradient.primaryColor

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)

            for (i in 0 until 3) {
                val angle = Math.toRadians((rotation + (i * 120)).toDouble())
                val radius = (size.width * 0.4f)
                val x = center.x + cos(angle).toFloat() * radius
                val y = center.y + sin(angle).toFloat() * radius + bounce

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            if (i % 2 == 0) primaryColor.copy(alpha = 0.06f) else aiAccent.copy(
                                alpha = 0.04f
                            ),
                            Color.Transparent
                        ),
                        center = Offset(x, y),
                        radius = 200.dp.toPx()
                    ),
                    center = Offset(x, y),
                    radius = 200.dp.toPx()
                )
            }
        }
    }
}
