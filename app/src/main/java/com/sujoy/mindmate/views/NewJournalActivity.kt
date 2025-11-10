package com.sujoy.mindmate.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.models.AnalyzedMoodObject
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.vm.NewJournalViewModel
import kotlinx.coroutines.launch

class NewJournalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                JournalScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JournalScreen(viewModel: NewJournalViewModel? = viewModel()) {
    val journalBody by viewModel?.journalBody?.collectAsState()
        ?: remember { mutableStateOf("Write anything that's on your mind...") }
    val isAnalyzing by viewModel?.isAnalyzing?.collectAsState()
        ?: remember { mutableStateOf(false) }
    val analysisResult by viewModel?.analysisResult?.collectAsState() ?: remember {
        mutableStateOf(
            null
        )
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }


    // Use LaunchedEffect to show the Snackbar or Bottom Sheet when the result changes
    LaunchedEffect(analysisResult) {
        analysisResult?.let { result ->
            result.fold(
                onSuccess = {
                    showSheet = true
                },
                onFailure = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Error: ${it.localizedMessage}")
                    }
                }
            )
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                viewModel?.onSnackbarShown() // Resets the state in VM
            },
            sheetState = sheetState
        ) {
            analysisResult?.getOrNull()?.let {
                showMoodSheet(
                    moodObject = it,
                    onDismiss = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showSheet = false
                                viewModel?.onSnackbarShown() // Resets the state in VM
                            }
                        }
                    }
                )
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 15.dp)
                    .fillMaxSize(),
            ) {
                Header()
                Spacer(modifier = Modifier.height(30.dp))
                TextField(
                    value = journalBody,
                    onValueChange = { viewModel?.updateJournalBody(it) },
                    placeholder = {
                        Text(
                            "Write anything that's on your mind...",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(15.dp)),
                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
//                        disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 40.sp,
                        fontSize = 24.sp
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (isAnalyzing) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        enabled = true,
                        onClick = {
                            viewModel?.onSubmitTap()
                        },
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 10.dp)
                    ) {
                        Text(
                            "Analyze Mood",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun showMoodSheet(moodObject: AnalyzedMoodObject, onDismiss: () -> Unit) {
    val emoji = when (moodObject.mood.uppercase()) {
        "HAPPY" -> "😄"
        "SAD" -> "😢"
        "ANGRY" -> "😠"
        "STRESSED" -> "😫"
        "ANXIOUS" -> "😟"
        "RELAXED" -> "😌"
        "MOTIVATED" -> "🔥"
        else -> "😐" // Neutral or default
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = emoji, fontSize = 80.sp)
        Text(
            text = moodObject.message,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Done")
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Header() {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
//        Icon(Icons.Filled.ArrowBackIosNew, tint = Color.Black, contentDescription = "Back Icon")
        Text(
            "Create New Journal",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JournalScreenPreview() {
    MindMateTheme {
        JournalScreen(null)
    }
}
