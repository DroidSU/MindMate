package com.sujoy.mindmate.ui.views.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.MoodsEnum
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.views.components.AnalysisSuccessDialog
import com.sujoy.mindmate.ui.views.components.EntrySheet
import com.sujoy.mindmate.ui.views.components.FluidMoodSlider
import com.sujoy.mindmate.ui.views.components.ModernActionButton
import com.sujoy.mindmate.ui.views.components.ModernSaveButton
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodColor

enum class JournalEntryStep {
    WRITING, MOOD_SELECTION
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryScreen(
    uiState: AppUiState,
    textContent: String,
    selectedMood: MoodsEnum,
    moodScore: Float,
    analyzedMoodObject: JournalAnalyzedDbModel,
    onTextChange: (String) -> Unit,
    onMoodSelected: (MoodsEnum) -> Unit,
    onMoodScoreChanged: (Float) -> Unit,
    onSaveClick: () -> Unit,
    onCloseDialog: () -> Unit,
) {
    val moodColor = getMoodColor(selectedMood)
    val animatedMoodColor by animateColorAsState(
        targetValue = moodColor,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "moodColor"
    )

    var showMoodPicker by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDialog by remember { mutableStateOf(false) }

    if (analyzedMoodObject.mood.isNotEmpty()) {
        showDialog = true
    }

    if (showDialog) {
        AnalysisSuccessDialog(
            analyzedMood = analyzedMoodObject,
            onDismiss = {
                showDialog = false
                onCloseDialog()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .blur(100.dp)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(animatedMoodColor.copy(alpha = 0.15f), Color.Transparent),
                    center = Offset(size.width * 0.2f, size.height * 0.2f),
                    radius = size.width * 1.5f
                )
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(animatedMoodColor.copy(alpha = 0.1f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.8f),
                    radius = size.width * 1.2f
                )
            )
        }

        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Reflect",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = (-1.5).sp
                )
                Text(
                    text = "Transform your feelings into words",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                EntrySheet(
                    text = textContent,
                    onTextChange = onTextChange,
                    moodColor = animatedMoodColor
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        AttachmentTool(Icons.Default.AddAPhoto, animatedMoodColor)
                        AttachmentTool(Icons.Default.Mic, animatedMoodColor)
                    }

                    ModernActionButton(
                        text = "Next",
                        isEnabled = textContent.isNotBlank(),
                        moodColor = animatedMoodColor,
                        onClick = { showMoodPicker = true }
                    )
                }
            }
        }

        if (showMoodPicker) {
            ModalBottomSheet(
                onDismissRequest = { showMoodPicker = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Subtle background ambient glow inside the sheet
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                            .blur(80.dp)
                            .align(Alignment.Center)
                    ) {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    animatedMoodColor.copy(alpha = 0.12f),
                                    Color.Transparent
                                ),
                                center = Offset(size.width / 2, size.height / 2),
                                radius = size.width * 0.8f
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Mood Check",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "How's your heart today?",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        FluidMoodSlider(
                            value = moodScore,
                            onValueChange = onMoodScoreChanged,
                            onMoodDetected = onMoodSelected,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        ModernSaveButton(
                            isLoading = uiState is AppUiState.Loading,
                            isEnabled = true,
                            moodColor = animatedMoodColor,
                            onClick = onSaveClick
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AttachmentTool(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    moodColor: Color
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clickable { /* TODO */ },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JournalEntryScreenPreview() {
    MindMateTheme {
        JournalEntryScreen(
            uiState = AppUiState.Idle,
            textContent = "Exploring the calm and quiet moments of the afternoon.",
            selectedMood = MoodsEnum.NEUTRAL,
            moodScore = 0.7f,
            analyzedMoodObject = JournalAnalyzedDbModel(
                id = "",
                journalId = "",
                sentimentScore = 0f,
                mood = "",
                message = "",
                timeStamp = 0
            ),
            onTextChange = {},
            onMoodSelected = {},
            onMoodScoreChanged = {},
            onSaveClick = {},
            onCloseDialog = {}
        )
    }
}
