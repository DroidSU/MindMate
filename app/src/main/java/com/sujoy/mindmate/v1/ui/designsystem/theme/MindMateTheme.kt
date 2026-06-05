package com.sujoy.mindmate.v1.ui.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens
import com.sujoy.mindmate.v1.ui.designsystem.tokens.RadiusTokens
import com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens
import com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens

internal val LightColorScheme = lightColorScheme(
    primary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightPrimary,
    secondary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightSecondary,
    background = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightBackground,
    surface = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightSurface,
    onPrimary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightSurface,
    onSecondary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightSurface,
    onBackground = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightTextPrimary,
    onSurface = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightTextPrimary,
    onSurfaceVariant = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightTextSecondary,
    outline = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightDivider
)

internal val DarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkPrimary,
    secondary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkSecondary,
    background = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkBackground,
    surface = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkSurface,
    onPrimary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkBackground,
    onSecondary = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkBackground,
    onBackground = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkTextPrimary,
    onSurface = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkTextPrimary,
    onSurfaceVariant = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkTextSecondary,
    outline = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkDivider
)

private val AppTypography = Typography(
    displayLarge = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.DisplayLarge,
    displayMedium = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.DisplayMedium,
    headlineLarge = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.HeadlineLarge,
    headlineMedium = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.HeadlineMedium,
    titleLarge = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.TitleLarge,
    titleMedium = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.TitleMedium,
    bodyLarge = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.BodyLarge,
    bodyMedium = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.BodyMedium,
    bodySmall = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.BodySmall,
    labelLarge = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.LabelLarge,
    labelMedium = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.LabelMedium,
    labelSmall = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.TypographyTokens.LabelSmall
)

object MindMateDesignSystem {
    val spacing: com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens =
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens
    val radius: com.sujoy.mindmate.v1.ui.designsystem.tokens.RadiusTokens =
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.RadiusTokens
}

internal val LocalSpacing =
    staticCompositionLocalOf { _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens }
internal val LocalRadius =
    staticCompositionLocalOf { _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.RadiusTokens }

@Composable
fun MindMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.DarkColorScheme else _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.LightColorScheme

    CompositionLocalProvider(
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.LocalSpacing provides _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens,
        _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.LocalRadius provides _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.RadiusTokens
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.AppTypography,
            content = content
        )
    }
}

object MindMateTheme {
    val spacing: com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens
        @Composable
        @ReadOnlyComposable
        get() = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.LocalSpacing.current

    val radius: com.sujoy.mindmate.v1.ui.designsystem.tokens.RadiusTokens
        @Composable
        @ReadOnlyComposable
        get() = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.theme.LocalRadius.current
}
