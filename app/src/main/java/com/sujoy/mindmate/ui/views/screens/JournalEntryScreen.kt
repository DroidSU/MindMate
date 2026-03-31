package com.sujoy.mindmate.ui.views.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodColor
import com.sujoy.mindmate.utils.UtilityMethods.Companion.getMoodEmoji

@Composable
fun JournalEntryScreen(
    uiState: AppUiState,
    textContent: String,
    selectedMood: MoodsEnum,
    analyzedMoodObject: JournalAnalyzedDbModel,
    onTextChange: (String) -> Unit,
    onMoodSelected: (MoodsEnum) -> Unit,
    onSaveClick: () -> Unit,
    onCloseDialog: () -> Unit,
) {
    val moodColor = getMoodColor(selectedMood)
    val animatedMoodColor by animateColorAsState(
        targetValue = moodColor,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "moodColor"
    )

    var showDialog by remember { mutableStateOf(false) }

    // Logic to show dialog when analysis result is updated (and not empty)
    if (analyzedMoodObject.mood.isNotEmpty()) {
        showDialog = true
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onCloseDialog() },
            confirmButton = {
                TextButton(onClick = { onCloseDialog() }) {
                    Text("OK")
                }
            },
            title = { Text("Analysis Result") },
            text = {
                Column {
                    Text("Detected Mood: ${analyzedMoodObject.mood}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Message: ${analyzedMoodObject.message}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Sentiment Score: ${analyzedMoodObject.sentimentScore}")
                }
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

                // Modern Minimalist Header
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

                // Interactive Horizontal Mood Ribbon
                MoodRibbon(
                    selectedMood = selectedMood,
                    onMoodSelected = onMoodSelected
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Translucent "Glass" Entry Sheet
                EntrySheet(
                    text = textContent,
                    onTextChange = onTextChange,
                    moodColor = animatedMoodColor
                )

                Spacer(modifier = Modifier.weight(1f))

                // Integrated Attachment & Action Bar
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

                    ModernSaveButton(
                        isLoading = uiState is AppUiState.Loading,
                        isEnabled = textContent.isNotBlank(),
                        moodColor = animatedMoodColor,
                        onClick = onSaveClick
                    )
                }
            }
        }
    }
}

@Composable
private fun MoodRibbon(
    selectedMood: MoodsEnum,
    onMoodSelected: (MoodsEnum) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(MoodsEnum.entries) { mood ->
            val isSelected = mood == selectedMood
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.3f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "scale"
            )

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) getMoodColor(mood).copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                    .clickable { onMoodSelected(mood) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getMoodEmoji(mood),
                    fontSize = if (isSelected) 34.sp else 28.sp
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 6.dp)
                            .size(4.dp)
                            .background(getMoodColor(mood), CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
private fun EntrySheet(
    text: String,
    onTextChange: (String) -> Unit,
    moodColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(moodColor.copy(alpha = 0.4f), Color.Transparent)
                ),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            Icon(
                imageVector = Icons.Rounded.AutoAwesome,
                contentDescription = null,
                tint = moodColor.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = {
                    Text(
                        "I am thinking about...",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    )
                },
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = moodColor
                ),
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Medium,
                    lineHeight = 38.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
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
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .clickable { /* TODO */ },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun ModernSaveButton(
    isLoading: Boolean,
    isEnabled: Boolean,
    moodColor: Color,
    onClick: () -> Unit
) {
    val containerColor =
        if (isEnabled) moodColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val contentColor =
        if (isEnabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)

    Button(
        onClick = onClick,
        enabled = isEnabled && !isLoading,
        modifier = Modifier
            .height(60.dp)
            .width(150.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JournalEntryScreenPreview() {
    MindMateTheme {
        JournalEntryScreen(
            uiState = AppUiState.Idle,
            textContent = "Exploring the calm and quiet moments of the afternoon.",
            selectedMood = MoodsEnum.RELAXED,
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
            onSaveClick = {},
            onCloseDialog = {}
        )
    }
}
