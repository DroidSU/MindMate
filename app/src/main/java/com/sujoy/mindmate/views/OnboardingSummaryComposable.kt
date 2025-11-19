package com.sujoy.mindmate.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sujoy.mindmate.ui.theme.MindMateTheme

@Composable
fun OnboardingSummary(onContinue: () -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun OnboardingSummaryPreview() {
    MindMateTheme {
        OnboardingSummary(onContinue = {})
    }
}