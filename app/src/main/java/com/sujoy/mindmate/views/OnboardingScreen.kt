package com.sujoy.mindmate.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sujoy.mindmate.R
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

@Composable
private fun OnboardingFlow(viewModel: OnboardingViewModel? = viewModel()) {
    val currentStep by viewModel?.currentStep?.collectAsState() ?: remember { mutableIntStateOf(1) }
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
            AnimatedContent(
                targetState = currentStep,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }, label = "Onboarding Animation"
            ) { step ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        when (step) {
                            0 -> WelcomeStep(onGetStarted = { viewModel?.nextStep() })
                            1 -> QuestionsStep(onContinue = { viewModel?.nextStep() })
                            2 -> SummaryStep(onFinish = {
                                val intent = Intent(context, NewJournalActivity::class.java)
                                context.startActivity(intent)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeStep(onGetStarted: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_sloth_meditate))

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            isPlaying = true,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.welcome_step_header),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

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
                    .clickable(onClick = onGetStarted)
            ) {
                Text(
                    stringResource(R.string.get_started),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SuggestionChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onTertiaryContainer

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = text, color = contentColor, style = MaterialTheme.typography.bodyMedium)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionsStep(onContinue: () -> Unit) {
    var habits by remember { mutableStateOf(TextFieldValue("")) }
    val selectedHabits = remember { mutableStateListOf<String>() }
    val habitSuggestions = listOf("Exercise", "Meditate", "Read", "Journal", "Hydrate", "Sleep")

    var moods by remember { mutableStateOf(TextFieldValue("")) }
    val selectedMoods = remember { mutableStateListOf<String>() }
    val moodSuggestions = listOf("Stressed", "Tired", "Anxious", "Happy", "Neutral", "Relaxed")

    var reminders by remember { mutableStateOf("") }

    var activeSuggestionFor by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "What are the top 3 habits you want to build or maintain?",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = habits,
            onValueChange = { value ->
                habits = value
                val currentHabits =
                    value.text.split(',').map(String::trim).filter(String::isNotEmpty)
                selectedHabits.clear()
                selectedHabits.addAll(currentHabits.filter { it in habitSuggestions })
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        activeSuggestionFor = "habits"
                    }
                },
            placeholder = { Text("e.g., Exercise, Meditate, Read") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        AnimatedVisibility(visible = activeSuggestionFor == "habits") {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Suggestions (select up to 3):",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    habitSuggestions.forEach { habit ->
                        SuggestionChip(
                            text = habit,
                            isSelected = habit in selectedHabits,
                            onClick = {
                                if (habit in selectedHabits) {
                                    selectedHabits.remove(habit)
                                } else {
                                    if (selectedHabits.size < 3) {
                                        selectedHabits.add(habit)
                                    }
                                }
                                val newText = selectedHabits.joinToString(", ")
                                habits = TextFieldValue(
                                    text = if (selectedHabits.isNotEmpty()) "$newText, " else "",
                                    selection = TextRange(if (selectedHabits.isNotEmpty()) newText.length + 2 else 0)
                                )
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "What moods usually break your routine?",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = moods,
            onValueChange = { value ->
                moods = value
                val currentMoods =
                    value.text.split(',').map(String::trim).filter(String::isNotEmpty)
                selectedMoods.clear()
                selectedMoods.addAll(currentMoods.filter { it in moodSuggestions })
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        activeSuggestionFor = "moods"
                    }
                },
            placeholder = { Text("e.g., Stressed, Tired, Anxious") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        AnimatedVisibility(visible = activeSuggestionFor == "moods") {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Suggestions (select up to 3):",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    moodSuggestions.forEach { mood ->
                        SuggestionChip(
                            text = mood,
                            isSelected = mood in selectedMoods,
                            onClick = {
                                if (mood in selectedMoods) {
                                    selectedMoods.remove(mood)
                                } else {
                                    if (selectedMoods.size < 3) {
                                        selectedMoods.add(mood)
                                    }
                                }
                                val newText = selectedMoods.joinToString(", ")
                                moods = TextFieldValue(
                                    text = if (selectedMoods.isNotEmpty()) "$newText, " else "",
                                    selection = TextRange(if (selectedMoods.isNotEmpty()) newText.length + 2 else 0)
                                )
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "When do you want preventive reminders?",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = reminders,
            onValueChange = { reminders = it },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        activeSuggestionFor = null
                    }
                },
            placeholder = { Text("e.g., In the morning, Before bed") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Do you want AI suggestions or just reminders?",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Handle simple mode */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Simple", color = MaterialTheme.colorScheme.onTertiary)
            }
            Button(
                onClick = { /* Handle smart mode */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Smart", color = MaterialTheme.colorScheme.onTertiary)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

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
                .clickable(onClick = onContinue)
        ) {
            Text(
                "Continue",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
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


@Preview(showBackground = true)
@Composable
private fun OnboardingFlowPreview() {
    MindMateTheme {
        OnboardingFlow(null)
    }
}
