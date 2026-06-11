package com.sujoy.mindmate.v2.ui.views.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sujoy.mindmate.R
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2ColorTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2RadiusTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2SpacingTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2TypographyTokens

@Composable
fun MiloWithBubble(
    modifier: Modifier = Modifier,
    onBubbleClick: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_sloth_meditate))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = V2ColorTokens.DeepIndigo,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp
            )
        ) {
            append("How was your day?\n")
        }
        withStyle(
            style = SpanStyle(
                color = V2ColorTokens.AiAccent,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Talk to me")
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Chat Bubble with Triangle Tail
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 60.dp),
                onClick = onBubbleClick
            )
        ) {
            Surface(
                color = V2ColorTokens.SurfaceLight,
                shape = RoundedCornerShape(V2RadiusTokens.Medium),
                shadowElevation = 2.dp,
                modifier = Modifier.padding(bottom = 0.dp)
            ) {
                Text(
                    text = annotatedText,
                    style = V2TypographyTokens.BodyMedium.copy(
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(
                        horizontal = V2SpacingTokens.MediumSmall,
                        vertical = 8.dp
                    )
                )
            }

            // Downward pointing tail
            Canvas(
                modifier = Modifier
                    .size(width = 12.dp, height = 6.dp)
                    .offset(y = (-1).dp) // Slight overlap to prevent a gap line
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width / 2f, size.height)
                    close()
                }
                drawPath(
                    path = path,
                    color = V2ColorTokens.SurfaceLight
                )
            }
        }

        // Lottie Animation
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(130.dp)
                .offset(y = (-4).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MiloWithBubblePreview() {
    MindMateV2Theme {
        MiloWithBubble()
    }
}