package com.sujoy.mindmate.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.vm.OnboardingViewModel

class OnboardingActivity : ComponentActivity() {
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
private fun OnboardingFlow(viewModel: OnboardingViewModel = viewModel()) {
    val currentStep by viewModel.currentStep.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 5 })
    val context = LocalContext.current

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setCurrentStep(pagerState.currentPage)
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
                        0 -> GetStarted(onGetStarted = { viewModel.nextStep() })
                        1 -> ChooseHabits(
                            onContinue = { viewModel.nextStep() },
                            onBack = { viewModel.previousStep() },
                            viewModel = viewModel
                        )

                        2 -> ChooseMoods(
                            onContinue = { viewModel.nextStep() },
                            onBack = { viewModel.previousStep() },
                            viewModel = viewModel
                        )

                        3 -> SetReminder(onContinue = {
                            onSelectionsComplete(context, viewModel)
                        })
                    }
                }
//                PageIndicator(
//                    pageCount = 4,
//                    currentPage = pagerState.currentPage,
//                    modifier = Modifier.padding(bottom = 28.dp)
//                )
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

private fun onSelectionsComplete(context: Context, viewModel: OnboardingViewModel) {
    viewModel.saveSelections()
    val intent = Intent(context, OnboardingSummaryActivity::class.java)
    context.startActivity(intent)
    (context as OnboardingActivity).finish()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingFlowPreview() {
    MindMateTheme {
        OnboardingFlow(viewModel())
    }
}
