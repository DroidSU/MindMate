package com.sujoy.mindmate.views.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.views.components.ChooseHabits
import com.sujoy.mindmate.views.components.ChooseMoods
import com.sujoy.mindmate.views.components.GetStarted
import com.sujoy.mindmate.views.components.SetReminder
import com.sujoy.mindmate.vm.OnboardingViewModel
import com.sujoy.mindmate.vm.ViewModelFactory
import kotlinx.coroutines.launch

class OnboardingActivity : ComponentActivity() {
    private val viewModel: OnboardingViewModel by viewModels {
        ViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                val currentStep by viewModel.currentStep.collectAsState()
                val selectedHabits by viewModel.selectedHabits.collectAsState()
                val selectedMoods by viewModel.selectedMoods.collectAsState()
                val pagerState = rememberPagerState(pageCount = { 4 })
                val scope = rememberCoroutineScope()
                val context = LocalContext.current

                // Sync Pager state -> ViewModel
                LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                    if (!pagerState.isScrollInProgress) {
                        viewModel.setCurrentStep(pagerState.currentPage)
                    }
                }

                // Sync ViewModel state -> Pager
                LaunchedEffect(currentStep) {
                    if (pagerState.currentPage != currentStep) {
                        scope.launch {
                            pagerState.animateScrollToPage(currentStep)
                        }
                    }
                }

                OnboardingFlow(
                    pagerState = pagerState,
                    onNextStep = viewModel::nextStep,
                    onPreviousStep = viewModel::previousStep,
                    selectedHabits = selectedHabits,
                    toggleHabit = viewModel::toggleHabitSelection,
                    selectedMoods = selectedMoods,
                    toggleMood = viewModel::toggleMoodSelection,
                    onSelectionsComplete = {
                        onSelectionsComplete(context, viewModel)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingFlow(
    pagerState: PagerState,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    selectedHabits: Set<String>,
    toggleHabit: (String) -> Unit,
    selectedMoods: Set<String>,
    toggleMood: (String) -> Unit,
    onSelectionsComplete: () -> Unit
) {
    val context = LocalContext.current

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
                    modifier = Modifier.weight(1f),
                    userScrollEnabled = false // To enforce button navigation
                ) { page ->
                    when (page) {
                        0 -> GetStarted(onGetStarted = onNextStep)
                        1 -> ChooseHabits(
                            onContinue = onNextStep,
                            onBack = onPreviousStep,
                            selectedHabits = selectedHabits,
                            toggleHabit = toggleHabit
                        )

                        2 -> ChooseMoods(
                            onContinue = onNextStep,
                            onBack = onPreviousStep,
                            selectedMoods = selectedMoods,
                            toggleMood = toggleMood
                        )

                        3 -> SetReminder(onContinue = onSelectionsComplete)
                    }
                }
                PageIndicator(
                    pageCount = pagerState.pageCount,
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

private fun onSelectionsComplete(context: Context, viewModel: OnboardingViewModel) {
    viewModel.saveSelections()
    val intent = Intent(context, OnboardingSummaryActivity::class.java)
    context.startActivity(intent)
    (context as? OnboardingActivity)?.finish()
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingFlowPreview() {
    MindMateTheme {
        OnboardingFlow(
            pagerState = rememberPagerState(pageCount = { 4 }),
            onNextStep = {},
            onPreviousStep = {},
            selectedHabits = setOf("Workout"),
            toggleHabit = {},
            selectedMoods = setOf("Happy", "Stressed"),
            toggleMood = {},
            onSelectionsComplete = {}
        )
    }
}