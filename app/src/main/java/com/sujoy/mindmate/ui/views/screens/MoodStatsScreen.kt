package com.sujoy.mindmate.ui.views.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.vm.StatsPeriod

@Composable
fun MoodStatsScreen(
    dailyStats: Map<Long, Float>,
    selectedPeriod: StatsPeriod,
    onPeriodSelected: (StatsPeriod) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Emotional Pulse",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = when (selectedPeriod) {
                        StatsPeriod.SEVEN_DAYS -> "Your vibes this week"
                        StatsPeriod.FOURTEEN_DAYS -> "Your vibes these two weeks"
                        StatsPeriod.THIRTY_DAYS -> "Your monthly journey"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }

            PeriodSelector(
                selectedPeriod = selectedPeriod,
                onPeriodSelected = onPeriodSelected
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
                .clip(RoundedCornerShape(32.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                            MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            val requiredCount = when (selectedPeriod) {
                StatsPeriod.SEVEN_DAYS -> 7
                StatsPeriod.FOURTEEN_DAYS -> 14
                StatsPeriod.THIRTY_DAYS -> 30
            }

            if (dailyStats.size < requiredCount) {
                EmptyStatsState(
                    message = when (selectedPeriod) {
                        StatsPeriod.SEVEN_DAYS -> "Need 7 days of data to show your weekly pulse."
                        StatsPeriod.FOURTEEN_DAYS -> "Need 14 days of data to show your bi-weekly pulse."
                        StatsPeriod.THIRTY_DAYS -> "Need 30 days of data to show your monthly pulse."
                    }
                )
            } else {
                MoodPulseGraph(dataPoints = dailyStats.values.toList())
            }
        }

        if (dailyStats.size >= (when (selectedPeriod) {
                StatsPeriod.SEVEN_DAYS -> 7
                StatsPeriod.FOURTEEN_DAYS -> 14
                StatsPeriod.THIRTY_DAYS -> 30
            })
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            MoodLegend()
        }
    }
}

@Composable
fun MoodPulseGraph(dataPoints: List<Float>) {
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(dataPoints) {
        animationProgress.animateTo(1f, animationSpec = tween(2000))
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp, vertical = 40.dp)) {
        if (dataPoints.isEmpty()) return@Canvas

        val width = size.width
        val height = size.height
        val spacing = width / (if (dataPoints.size > 1) dataPoints.size - 1 else 1)

        val path = Path()
        val fillPath = Path()

        // Normalize -1.0..1.0 to 0.0..1.0
        val points = dataPoints.mapIndexed { index, value ->
            val x = index * spacing
            val normalizedValue = (value + 1f) / 2f
            val animatedValue = normalizedValue * animationProgress.value
            val y = height - (animatedValue.coerceIn(0f, 1f) * height)
            Offset(x, y)
        }

        if (points.isNotEmpty()) {
            path.moveTo(points.first().x, points.first().y)
            fillPath.moveTo(points.first().x, height)
            fillPath.lineTo(points.first().x, points.first().y)

            for (i in 0 until points.size - 1) {
                val p1 = points[i]
                val p2 = points[i + 1]

                val controlPoint1 = Offset(p1.x + (p2.x - p1.x) / 2, p1.y)
                val controlPoint2 = Offset(p1.x + (p2.x - p1.x) / 2, p2.y)

                path.cubicTo(
                    controlPoint1.x, controlPoint1.y,
                    controlPoint2.x, controlPoint2.y,
                    p2.x, p2.y
                )

                fillPath.cubicTo(
                    controlPoint1.x, controlPoint1.y,
                    controlPoint2.x, controlPoint2.y,
                    p2.x, p2.y
                )
            }

            fillPath.lineTo(points.last().x, height)
            fillPath.close()

            // Multi-gradient Fill
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.4f * animationProgress.value),
                        secondaryColor.copy(alpha = 0.1f * animationProgress.value),
                        Color.Transparent
                    )
                )
            )

            // Glowing Line
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(primaryColor, secondaryColor, tertiaryColor)
                ),
                style = Stroke(
                    width = 6.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )

            // Points with Orbs
            points.forEachIndexed { index, point ->
                if (index == points.size - 1 || index == 0 || (points.size > 5 && index % (points.size / 3) == 0)) {
                    drawCircle(
                        color = Color.White,
                        radius = 6.dp.toPx(),
                        center = point
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(primaryColor, Color.Transparent),
                            center = point,
                            radius = 12.dp.toPx()
                        ),
                        radius = 12.dp.toPx(),
                        center = point
                    )
                    drawCircle(
                        color = primaryColor,
                        radius = 3.5.dp.toPx(),
                        center = point
                    )
                }
            }
        }
    }
}

@Composable
fun MoodLegend() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Negative",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Your Pulse",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            "Positive",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun PeriodSelector(
    selectedPeriod: StatsPeriod,
    onPeriodSelected: (StatsPeriod) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(4.dp)
    ) {
        StatsPeriod.entries.forEach { period ->
            val isSelected = selectedPeriod == period
            val contentColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )
            val bgColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(bgColor)
                    .clickable { onPeriodSelected(period) }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (period) {
                        StatsPeriod.SEVEN_DAYS -> "7D"
                        StatsPeriod.FOURTEEN_DAYS -> "14D"
                        StatsPeriod.THIRTY_DAYS -> "30D"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
fun EmptyStatsState(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.BarChart,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your inner sea is calm",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoodStatsScreenPreview() {
    MindMateTheme {
        MoodStatsScreen(
            dailyStats = sortedMapOf(
                System.currentTimeMillis() - 86400000 * 6 to 0.7f,
                System.currentTimeMillis() - 86400000 * 5 to -0.2f,
                System.currentTimeMillis() - 86400000 * 4 to 1.0f,
                System.currentTimeMillis() - 86400000 * 3 to 0.1f,
                System.currentTimeMillis() - 86400000 * 2 to -0.8f,
                System.currentTimeMillis() - 86400000 * 1 to 0.9f,
                System.currentTimeMillis() to 0.4f
            ),
            selectedPeriod = StatsPeriod.SEVEN_DAYS,
            onPeriodSelected = {}
        )
    }
}
