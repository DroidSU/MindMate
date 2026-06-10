package com.sujoy.mindmate.v2.ui.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2ColorTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2RadiusTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2SpacingTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2TypographyTokens

@Immutable
data class V2AiGradient(
    val brush: Brush,
    val primaryColor: Color
)

val LocalV2AiGradient = staticCompositionLocalOf {
    V2AiGradient(
        brush = Brush.linearGradient(listOf(Color.Unspecified, Color.Unspecified)),
        primaryColor = Color.Unspecified
    )
}

val LocalV2Spacing = staticCompositionLocalOf { V2SpacingTokens }
val LocalV2Radius = staticCompositionLocalOf { V2RadiusTokens }

private val V2LightColorScheme = lightColorScheme(
    primary = V2ColorTokens.PrimarySlate,
    onPrimary = V2ColorTokens.SurfaceLight,
    primaryContainer = V2ColorTokens.BackgroundLight,
    onPrimaryContainer = V2ColorTokens.PrimarySlate,
    secondary = V2ColorTokens.AiAccent,
    background = V2ColorTokens.BackgroundLight,
    surface = V2ColorTokens.SurfaceLight,
    onBackground = V2ColorTokens.TextPrimaryLight,
    onSurface = V2ColorTokens.TextPrimaryLight,
    onSurfaceVariant = V2ColorTokens.TextSecondaryLight,
    outline = V2ColorTokens.DividerLight
)

private val V2DarkColorScheme = darkColorScheme(
    primary = V2ColorTokens.PrimarySlate,
    onPrimary = V2ColorTokens.TextPrimaryDark,
    primaryContainer = V2ColorTokens.SurfaceDark,
    onPrimaryContainer = V2ColorTokens.TextPrimaryDark,
    secondary = V2ColorTokens.AiAccent,
    background = V2ColorTokens.BackgroundDark,
    surface = V2ColorTokens.SurfaceDark,
    onBackground = V2ColorTokens.TextPrimaryDark,
    onSurface = V2ColorTokens.TextPrimaryDark,
    onSurfaceVariant = V2ColorTokens.TextSecondaryDark,
    outline = V2ColorTokens.DividerDark
)

private val V2Typography = Typography(
    headlineLarge = V2TypographyTokens.HeadlineLarge,
    headlineMedium = V2TypographyTokens.HeadlineMedium,
    titleLarge = V2TypographyTokens.TitleLarge,
    bodyLarge = V2TypographyTokens.BodyLarge,
    bodyMedium = V2TypographyTokens.BodyMedium,
    labelLarge = V2TypographyTokens.LabelLarge
)

@Composable
fun MindMateV2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) V2DarkColorScheme else V2LightColorScheme

    val aiGradient = V2AiGradient(
        brush = Brush.linearGradient(
            colors = listOf(V2ColorTokens.AiGradientStart, V2ColorTokens.AiGradientEnd)
        ),
        primaryColor = V2ColorTokens.AiAccent
    )

    CompositionLocalProvider(
        LocalV2AiGradient provides aiGradient,
        LocalV2Spacing provides V2SpacingTokens,
        LocalV2Radius provides V2RadiusTokens
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = V2Typography,
            content = content
        )
    }
}

object MindMateV2Theme {
    val aiGradient: V2AiGradient
        @Composable
        get() = LocalV2AiGradient.current

    val spacing: V2SpacingTokens
        @Composable
        get() = LocalV2Spacing.current

    val radius: V2RadiusTokens
        @Composable
        get() = LocalV2Radius.current
}
