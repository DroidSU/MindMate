package com.sujoy.mindmate.ui.views.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.ui.theme.md_theme_dark_primary
import com.sujoy.mindmate.ui.theme.md_theme_dark_secondary
import com.sujoy.mindmate.ui.theme.md_theme_light_primary
import com.sujoy.mindmate.ui.theme.md_theme_light_secondary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AuthVisuals(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()
    val infiniteTransition = rememberInfiniteTransition(label = "auth_bg")

    val primaryColor = if (isDark) md_theme_dark_primary else md_theme_light_primary
    val secondaryColor = if (isDark) md_theme_dark_secondary else md_theme_light_secondary

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)

            // Draw floating 3D-like spheres
            for (i in 0 until 5) {
                val angle = Math.toRadians((rotation + (i * 72)).toDouble())
                val radius = (size.width * 0.3f) + (sin(angle.toFloat()) * 20)
                val x = center.x + cos(angle).toFloat() * radius
                val y = center.y + sin(angle).toFloat() * radius + bounce

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = if (isDark) 0.4f else 0.2f),
                            secondaryColor.copy(alpha = if (isDark) 0.1f else 0.05f),
                            Color.Transparent
                        ),
                        center = Offset(x, y),
                        radius = 120.dp.toPx()
                    ),
                    center = Offset(x, y),
                    radius = 120.dp.toPx()
                )
            }
        }
    }
}
