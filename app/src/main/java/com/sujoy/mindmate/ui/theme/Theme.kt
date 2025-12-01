package com.sujoy.mindmate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class GradientColors(
    val buttonStart: Color,
    val buttonEnd: Color
)

private val LightGradientColors = GradientColors(
    buttonStart = md_theme_light_button_primary,
    buttonEnd = md_theme_light_button_secondary
)

private val DarkGradientColors = GradientColors(
    buttonStart = md_theme_dark_button_primary,
    buttonEnd = md_theme_dark_button_secondary
)

val LocalGradientColors = staticCompositionLocalOf { LightGradientColors }

val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surface,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surface,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

// --- Happy Theme Color Schemes ---

val HappyLightColorScheme = lightColorScheme(
    primary = happy_theme_light_primary,
    onPrimary = happy_theme_light_onPrimary,
    primaryContainer = happy_theme_light_primaryContainer,
    onPrimaryContainer = happy_theme_light_onPrimaryContainer,
    secondary = happy_theme_light_secondary,
    onSecondary = happy_theme_light_onSecondary,
    secondaryContainer = happy_theme_light_secondaryContainer,
    onSecondaryContainer = happy_theme_light_onSecondaryContainer,
    background = happy_theme_light_background,
    onBackground = happy_theme_light_onBackground,
    surface = happy_theme_light_surface,
    onSurface = happy_theme_light_onSurface,
    surfaceVariant = happy_theme_light_surfaceVariant,
    onSurfaceVariant = happy_theme_light_onSurfaceVariant,
    // --- Reusing default values for consistency ---
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val HappyDarkColorScheme = darkColorScheme(
    primary = happy_theme_dark_primary,
    onPrimary = happy_theme_dark_onPrimary,
    primaryContainer = happy_theme_dark_primaryContainer,
    onPrimaryContainer = happy_theme_dark_onPrimaryContainer,
    secondary = happy_theme_dark_secondary,
    onSecondary = happy_theme_dark_onSecondary,
    secondaryContainer = happy_theme_dark_secondaryContainer,
    onSecondaryContainer = happy_theme_dark_onSecondaryContainer,
    background = happy_theme_dark_background,
    onBackground = happy_theme_dark_onBackground,
    surface = happy_theme_dark_surface,
    onSurface = happy_theme_dark_onSurface,
    surfaceVariant = happy_theme_dark_surfaceVariant,
    onSurfaceVariant = happy_theme_dark_onSurfaceVariant,
    // --- Reusing default values ---
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)

// --- Calm Theme Color Schemes ---

val CalmLightColorScheme = lightColorScheme(
    primary = calm_theme_light_primary,
    onPrimary = calm_theme_light_onPrimary,
    primaryContainer = calm_theme_light_primaryContainer,
    onPrimaryContainer = calm_theme_light_onPrimaryContainer,
    secondary = calm_theme_light_secondary,
    onSecondary = calm_theme_light_onSecondary,
    background = calm_theme_light_background,
    onBackground = calm_theme_light_onBackground,
    surface = calm_theme_light_surface,
    onSurface = calm_theme_light_onSurface,
    surfaceVariant = calm_theme_light_surfaceVariant,
    onSurfaceVariant = calm_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val CalmDarkColorScheme = darkColorScheme(
    primary = calm_theme_dark_primary,
    onPrimary = calm_theme_dark_onPrimary,
    primaryContainer = calm_theme_dark_primaryContainer,
    onPrimaryContainer = calm_theme_dark_onPrimaryContainer,
    secondary = calm_theme_dark_secondary,
    onSecondary = calm_theme_dark_onSecondary,
    background = calm_theme_dark_background,
    onBackground = calm_theme_dark_onBackground,
    surface = calm_theme_dark_surface,
    onSurface = calm_theme_dark_onSurface,
    surfaceVariant = calm_theme_dark_surfaceVariant,
    onSurfaceVariant = calm_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)

// --- Angry Theme Color Schemes ---

val AngryLightColorScheme = lightColorScheme(
    primary = angry_theme_light_primary,
    onPrimary = angry_theme_light_onPrimary,
    primaryContainer = angry_theme_light_primaryContainer,
    onPrimaryContainer = angry_theme_light_onPrimaryContainer,
    secondary = angry_theme_light_secondary,
    onSecondary = angry_theme_light_onSecondary,
    background = angry_theme_light_background,
    onBackground = angry_theme_light_onBackground,
    surface = angry_theme_light_surface,
    onSurface = angry_theme_light_onSurface,
    surfaceVariant = angry_theme_light_surfaceVariant,
    onSurfaceVariant = angry_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)


val AngryDarkColorScheme = darkColorScheme(
    primary = angry_theme_dark_primary,
    onPrimary = angry_theme_dark_onPrimary,
    primaryContainer = angry_theme_dark_primaryContainer,
    onPrimaryContainer = angry_theme_dark_onPrimaryContainer,
    secondary = angry_theme_dark_secondary,
    onSecondary = angry_theme_dark_onSecondary,
    background = angry_theme_dark_background,
    onBackground = angry_theme_dark_onBackground,
    surface = angry_theme_dark_surface,
    onSurface = angry_theme_dark_onSurface,
    surfaceVariant = angry_theme_dark_surfaceVariant,
    onSurfaceVariant = angry_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)


// --- Sad Theme Color Schemes ---

val SadLightColorScheme = lightColorScheme(
    primary = sad_theme_light_primary,
    onPrimary = sad_theme_light_onPrimary,
    primaryContainer = sad_theme_light_primaryContainer,
    onPrimaryContainer = sad_theme_light_onPrimaryContainer,
    secondary = sad_theme_light_secondary,
    onSecondary = sad_theme_light_onSecondary,
    background = sad_theme_light_background,
    onBackground = sad_theme_light_onBackground,
    surface = sad_theme_light_surface,
    onSurface = sad_theme_light_onSurface,
    surfaceVariant = sad_theme_light_surfaceVariant,
    onSurfaceVariant = sad_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val SadDarkColorScheme = darkColorScheme(
    primary = sad_theme_dark_primary,
    onPrimary = sad_theme_dark_onPrimary,
    primaryContainer = sad_theme_dark_primaryContainer,
    onPrimaryContainer = sad_theme_dark_onPrimaryContainer,
    secondary = sad_theme_dark_secondary,
    onSecondary = sad_theme_dark_onSecondary,
    background = sad_theme_dark_background,
    onBackground = sad_theme_dark_onBackground,
    surface = sad_theme_dark_surface,
    onSurface = sad_theme_dark_onSurface,
    surfaceVariant = sad_theme_dark_surfaceVariant,
    onSurfaceVariant = sad_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)

// --- Anxious Theme Color Schemes ---

val AnxiousLightColorScheme = lightColorScheme(
    primary = anxious_theme_light_primary,
    onPrimary = anxious_theme_light_onPrimary,
    primaryContainer = anxious_theme_light_primaryContainer,
    onPrimaryContainer = anxious_theme_light_onPrimaryContainer,
    secondary = anxious_theme_light_secondary,
    onSecondary = anxious_theme_light_onSecondary,
    background = anxious_theme_light_background,
    onBackground = anxious_theme_light_onBackground,
    surface = anxious_theme_light_surface,
    onSurface = anxious_theme_light_onSurface,
    surfaceVariant = anxious_theme_light_surfaceVariant,
    onSurfaceVariant = anxious_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val AnxiousDarkColorScheme = darkColorScheme(
    primary = anxious_theme_dark_primary,
    onPrimary = anxious_theme_dark_onPrimary,
    primaryContainer = anxious_theme_dark_primaryContainer,
    onPrimaryContainer = anxious_theme_dark_onPrimaryContainer,
    secondary = anxious_theme_dark_secondary,
    onSecondary = anxious_theme_dark_onSecondary,
    background = anxious_theme_dark_background,
    onBackground = anxious_theme_dark_onBackground,
    surface = anxious_theme_dark_surface,
    onSurface = anxious_theme_dark_onSurface,
    surfaceVariant = anxious_theme_dark_surfaceVariant,
    onSurfaceVariant = anxious_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)

// --- Tired Theme Color Schemes ---

val TiredLightColorScheme = lightColorScheme(
    primary = tired_theme_light_primary, onPrimary = tired_theme_light_onPrimary,
    primaryContainer = tired_theme_light_primaryContainer,
    onPrimaryContainer = tired_theme_light_onPrimaryContainer,
    secondary = tired_theme_light_secondary,
    onSecondary = tired_theme_light_onSecondary,
    background = tired_theme_light_background,
    onBackground = tired_theme_light_onBackground,
    surface = tired_theme_light_surface,
    onSurface = tired_theme_light_onSurface,
    surfaceVariant = tired_theme_light_surfaceVariant,
    onSurfaceVariant = tired_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val TiredDarkColorScheme = darkColorScheme(
    primary = tired_theme_dark_primary,
    onPrimary = tired_theme_dark_onPrimary,
    primaryContainer = tired_theme_dark_primaryContainer,
    onPrimaryContainer = tired_theme_dark_onPrimaryContainer,
    secondary = tired_theme_dark_secondary,
    onSecondary = tired_theme_dark_onSecondary,
    background = tired_theme_dark_background,
    onBackground = tired_theme_dark_onBackground,
    surface = tired_theme_dark_surface,
    onSurface = tired_theme_dark_onSurface,
    surfaceVariant = tired_theme_dark_surfaceVariant,
    onSurfaceVariant = tired_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)


// --- Lonely Theme Color Schemes ---

val LonelyLightColorScheme = lightColorScheme(
    primary = lonely_theme_light_primary,
    onPrimary = lonely_theme_light_onPrimary,
    primaryContainer = lonely_theme_light_primaryContainer,
    onPrimaryContainer = lonely_theme_light_onPrimaryContainer,
    secondary = lonely_theme_light_secondary,
    onSecondary = lonely_theme_light_onSecondary,
    background = lonely_theme_light_background,
    onBackground = lonely_theme_light_onBackground,
    surface = lonely_theme_light_surface,
    onSurface = lonely_theme_light_onSurface,
    surfaceVariant = lonely_theme_light_surfaceVariant,
    onSurfaceVariant = lonely_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val LonelyDarkColorScheme = darkColorScheme(
    primary = lonely_theme_dark_primary,
    onPrimary = lonely_theme_dark_onPrimary,
    primaryContainer = lonely_theme_dark_primaryContainer,
    onPrimaryContainer = lonely_theme_dark_onPrimaryContainer,
    secondary = lonely_theme_dark_secondary,
    onSecondary = lonely_theme_dark_onSecondary,
    background = lonely_theme_dark_background,
    onBackground = lonely_theme_dark_onBackground,
    surface = lonely_theme_dark_surface,
    onSurface = lonely_theme_dark_onSurface,
    surfaceVariant = lonely_theme_dark_surfaceVariant,
    onSurfaceVariant = lonely_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)

// --- Bored Theme Color Schemes ---

val BoredLightColorScheme = lightColorScheme(
    primary = bored_theme_light_primary,
    onPrimary = bored_theme_light_onPrimary,
    primaryContainer = bored_theme_light_primaryContainer,
    onPrimaryContainer = bored_theme_light_onPrimaryContainer,
    secondary = bored_theme_light_secondary,
    onSecondary = bored_theme_light_onSecondary,
    background = bored_theme_light_background,
    onBackground = bored_theme_light_onBackground,
    surface = bored_theme_light_surface,
    onSurface = bored_theme_light_onSurface,
    surfaceVariant = bored_theme_light_surfaceVariant,
    onSurfaceVariant = bored_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer
)

val BoredDarkColorScheme = darkColorScheme(
    primary = bored_theme_dark_primary,
    onPrimary = bored_theme_dark_onPrimary,
    primaryContainer = bored_theme_dark_primaryContainer,
    onPrimaryContainer = bored_theme_dark_onPrimaryContainer,
    secondary = bored_theme_dark_secondary,
    onSecondary = bored_theme_dark_onSecondary,
    background = bored_theme_dark_background,
    onBackground = bored_theme_dark_onBackground,
    surface = bored_theme_dark_surface,
    onSurface = bored_theme_dark_onSurface,
    surfaceVariant = bored_theme_dark_surfaceVariant,
    onSurfaceVariant = bored_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer
)







@Composable
fun MindMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    colorScheme: ColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    content: @Composable () -> Unit
) {
    val gradientColors = if (darkTheme) DarkGradientColors else LightGradientColors

    CompositionLocalProvider(LocalGradientColors provides gradientColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
