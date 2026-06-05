package com.sujoy.mindmate.v1.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.sujoy.mindmate.v1.data.models.AppUiState
import com.sujoy.mindmate.v1.ui.designsystem.components.MindMateButton
import com.sujoy.mindmate.v1.ui.designsystem.components.MindMateIllustrationCard
import com.sujoy.mindmate.v1.ui.designsystem.theme.MindMateTheme
import com.sujoy.mindmate.v1.ui.views.components.AuthVisuals

@Composable
fun AuthScreen(
    uiState: AppUiState,
    onGoogleSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AuthVisuals(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(MindMateTheme.spacing.ExtraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MindMateIllustrationCard()

            Spacer(modifier = Modifier.height(MindMateTheme.spacing.ExtraLarge))

            Text(
                text = "Welcome to MindMate",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(MindMateTheme.spacing.Small))

            Text(
                text = "Your personal AI companion for mental wellness and self-discovery.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MindMateTheme.spacing.Massive))

            MindMateButton(
                onClick = onGoogleSignInClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is AppUiState.Loading
            ) {
                Text("Continue with Google")
            }

            Spacer(modifier = Modifier.height(MindMateTheme.spacing.Medium))

            Text(
                text = "By continuing, you agree to our Terms and Privacy Policy.",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}
