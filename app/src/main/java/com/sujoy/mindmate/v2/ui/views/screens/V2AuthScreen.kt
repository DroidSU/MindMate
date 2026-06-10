package com.sujoy.mindmate.v2.ui.views.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sujoy.mindmate.v1.data.models.AppUiState
import com.sujoy.mindmate.v2.ui.designsystem.components.V2MindMateButton
import com.sujoy.mindmate.v2.ui.designsystem.components.V2MindMateIllustrationCard
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import com.sujoy.mindmate.v2.ui.views.components.V2AuthVisuals

@Composable
fun V2AuthScreen(
    uiState: AppUiState,
    onGoogleSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        V2AuthVisuals(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(MindMateV2Theme.spacing.ExtraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            V2MindMateIllustrationCard()

            Spacer(modifier = Modifier.height(MindMateV2Theme.spacing.ExtraLarge))

            Text(
                text = "Welcome to MindMate",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(MindMateV2Theme.spacing.Small))

            Text(
                text = "Your intelligent companion for a mindful life.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MindMateV2Theme.spacing.Massive))

            V2MindMateButton(
                onClick = onGoogleSignInClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is AppUiState.Loading
            ) {
                Text("Continue with Google")
            }

            Spacer(modifier = Modifier.height(MindMateV2Theme.spacing.Medium))

            Text(
                text = "By continuing, you agree to our Terms and Privacy Policy.",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun V2AuthScreenPreview() {
    MindMateV2Theme {
        V2AuthScreen(uiState = AppUiState.Idle, onGoogleSignInClick = {})
    }
}