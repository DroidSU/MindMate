package com.sujoy.mindmate.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.ui.theme.LocalGradientColors
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.vm.OnboardingViewModel

class OnboardingScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                OnboardingFlow()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingFlow(viewModel: OnboardingViewModel? = viewModel()) {
    val currentStep by viewModel?.currentStep?.collectAsState() ?: remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(pagerState.currentPage) {
        viewModel?.setCurrentStep(pagerState.currentPage)
    }

    LaunchedEffect(currentStep) {
        if (pagerState.currentPage != currentStep) {
            pagerState.animateScrollToPage(currentStep)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    when (page) {
                        0 -> GetStarted(onGetStarted = { viewModel?.nextStep() })
                        1 -> ChooseHabits(
                            onContinue = { viewModel?.nextStep() },
                            onBack = { viewModel?.previousStep() }
                        )

                        2 -> ChooseMoods(
                            onContinue = { viewModel?.nextStep() },
                            onBack = { viewModel?.previousStep() }
                        )

                        3 -> SetReminder(onContinue = { viewModel?.nextStep() })
                        4 -> OnboardingSummary(onContinue = { viewModel?.finishOnboarding() })
                    }
                }
                PageIndicator(
                    pageCount = 5,
                    currentPage = pagerState.currentPage,
                    modifier = Modifier.padding(bottom = 28.dp)
                )
            }
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0 until pageCount) {
            val width by animateDpAsState(
                targetValue = if (i == currentPage) 32.dp else 12.dp,
                label = "Indicator Width"
            )
            Box(
                modifier = Modifier
                    .size(height = 12.dp, width = width)
                    .clip(CircleShape)
                    .background(
                        if (i == currentPage) MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                    )
            )
        }
    }
}

@Composable
fun SummaryStep(onFinish: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Great. I’ll watch for these moods and protect these habits.",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
        Spacer(modifier = Modifier.height(48.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
                .shadow(10.dp, RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LocalGradientColors.current.buttonStart,
                            LocalGradientColors.current.buttonEnd
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable(onClick = onFinish)
        ) {
            Text(
                "Finish",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingFlowPreview() {
    MindMateTheme {
        OnboardingFlow(null)
    }
}
