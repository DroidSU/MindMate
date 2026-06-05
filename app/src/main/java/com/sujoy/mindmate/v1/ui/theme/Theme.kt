package com.sujoy.mindmate.v1.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.sujoy.mindmate.v1.ui.designsystem.theme.DarkColorScheme as NewDarkColorScheme
import com.sujoy.mindmate.v1.ui.designsystem.theme.LightColorScheme as NewLightColorScheme
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme as NewMindMateTheme
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme as NewMindMateThemeObject

val DarkColorScheme = NewDarkColorScheme
val LightColorScheme = NewLightColorScheme

data class GradientColors(
    val buttonStart: Color,
    val buttonEnd: Color
)

val LocalGradientColors = staticCompositionLocalOf {
    _root_ide_package_.com.sujoy.mindmate.v1.ui.theme.GradientColors(
        Color.Unspecified,
        Color.Unspecified
    )
}

@Composable
fun MindMateTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val gradientColors = if (darkTheme) {
        _root_ide_package_.com.sujoy.mindmate.v1.ui.theme.GradientColors(
            buttonStart = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkPrimary,
            buttonEnd = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.DarkSecondary
        )
    } else {
        _root_ide_package_.com.sujoy.mindmate.v1.ui.theme.GradientColors(
            buttonStart = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightPrimary,
            buttonEnd = _root_ide_package_.com.sujoy.mindmate.v1.ui.designsystem.tokens.ColorTokens.LightSecondary
        )
    }

    CompositionLocalProvider(_root_ide_package_.com.sujoy.mindmate.v1.ui.theme.LocalGradientColors provides gradientColors) {
        NewMindMateTheme(darkTheme = darkTheme, content = content)
    }
}

object MindMateTheme {
    val spacing: com.sujoy.mindmate.v1.ui.theme.LegacySpacing
        @Composable
        @androidx.compose.runtime.ReadOnlyComposable
        get() = _root_ide_package_.com.sujoy.mindmate.v1.ui.theme.LegacySpacing(
            NewMindMateThemeObject.spacing
        )
}

class LegacySpacing(private val tokens: com.sujoy.mindmate.v1.ui.designsystem.tokens.SpacingTokens) {
    val default: Dp get() = tokens.None
    val xs: Dp get() = tokens.ExtraSmall
    val sm: Dp get() = tokens.Small
    val md: Dp get() = tokens.Medium
    val lg: Dp get() = tokens.Large
    val xl: Dp get() = tokens.ExtraLarge
    val xxl: Dp get() = tokens.ExtraHuge
    val xxxl: Dp get() = tokens.Massive
}
