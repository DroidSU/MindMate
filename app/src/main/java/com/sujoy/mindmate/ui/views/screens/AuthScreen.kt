package com.sujoy.mindmate.ui.views.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.theme.md_theme_dark_background
import com.sujoy.mindmate.ui.theme.md_theme_dark_onSurface
import com.sujoy.mindmate.ui.theme.md_theme_dark_onSurfaceVariant
import com.sujoy.mindmate.ui.theme.md_theme_dark_outlineVariant
import com.sujoy.mindmate.ui.theme.md_theme_light_background
import com.sujoy.mindmate.ui.theme.md_theme_light_onSurface
import com.sujoy.mindmate.ui.theme.md_theme_light_onSurfaceVariant
import com.sujoy.mindmate.ui.theme.md_theme_light_outlineVariant
import com.sujoy.mindmate.ui.views.components.AuthVisuals
import com.sujoy.mindmate.ui.views.components.GoogleSignInButton

@Composable
fun AuthScreen(
    uiState: AppUiState,
    onGoogleSignInClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) md_theme_dark_background else md_theme_light_background
    val titleColor = if (isDark) md_theme_dark_onSurface else md_theme_light_onSurface
    val bodyColor = if (isDark) md_theme_dark_onSurfaceVariant else md_theme_light_onSurfaceVariant
    val footerColor = if (isDark) md_theme_dark_outlineVariant else md_theme_light_outlineVariant
    val glassColor = if (isDark) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.05f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Animated Background Visuals
        AuthVisuals(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    glassColor,
                                    glassColor.copy(alpha = 0.05f)
                                )
                            )
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.mindmate_transparent),
                    contentDescription = "MindMate Logo",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Text content with 3D feel
            Text(
                text = "Welcome to MindMate",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black,
                color = titleColor,
                textAlign = TextAlign.Center,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your safe space for mental clarity and emotional growth. Begin your journey today.",
                style = MaterialTheme.typography.bodyLarge,
                color = bodyColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Google Sign In Button
            GoogleSignInButton(
                onClick = onGoogleSignInClick,
                isLoading = uiState is AppUiState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            // Error Message
            AnimatedVisibility(
                visible = uiState is AppUiState.Error,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                if (uiState is AppUiState.Error) {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Secure. Private. Mindful.",
                style = MaterialTheme.typography.labelMedium,
                color = footerColor,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    MindMateTheme {
        AuthScreen(
            uiState = AppUiState.Idle,
            onGoogleSignInClick = {}
        )
    }
}
