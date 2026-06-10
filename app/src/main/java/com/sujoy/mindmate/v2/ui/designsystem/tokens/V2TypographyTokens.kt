package com.sujoy.mindmate.v2.ui.designsystem.tokens

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.R

object V2TypographyTokens {
    val SanctuaryFontFamily = FontFamily(
        Font(R.font.playfairdisplay_variablefont_weight, FontWeight.Normal),
        Font(R.font.playfairdisplay_variablefont_weight, FontWeight.Bold),
        Font(R.font.playfairdisplay_variablefont_weight, FontWeight.SemiBold)
    )

    val IntelligenceFontFamily = FontFamily(
        Font(R.font.plusjakartasans_variablefont_wght, FontWeight.Normal),
        Font(R.font.plusjakartasans_variablefont_wght, FontWeight.Medium),
        Font(R.font.plusjakartasans_variablefont_wght, FontWeight.Bold),
        Font(R.font.plusjakartasans_variablefont_wght, FontWeight.SemiBold)
    )

    // --- HEADLINES (Sanctuary - Serif) ---
    val HeadlineLarge = TextStyle(
        fontFamily = SanctuaryFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    )

    val HeadlineMedium = TextStyle(
        fontFamily = SanctuaryFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )

    val TitleLarge = TextStyle(
        fontFamily = SanctuaryFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )

    // --- BODY & LABELS (Intelligence - Sans-serif) ---
    val BodyLarge = TextStyle(
        fontFamily = IntelligenceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    val BodyMedium = TextStyle(
        fontFamily = IntelligenceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )

    val LabelLarge = TextStyle(
        fontFamily = IntelligenceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )

    val HeroHeader = HeadlineLarge.copy(
        fontFamily = IntelligenceFontFamily,
        fontWeight = FontWeight.Bold,
        color = V2ColorTokens.DeepIndigo,
        fontSize = 36.sp,
        lineHeight = 44.sp
    )

    val BodyMuted = BodyMedium.copy(
        fontFamily = IntelligenceFontFamily,
        color = V2ColorTokens.TextMuted
    )
}
