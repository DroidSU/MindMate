package com.sujoy.mindmate.views.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.models.AnalyzedMoodObject
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.utils.UtilityMethods
import com.sujoy.mindmate.vm.NewJournalViewModel
import kotlinx.coroutines.delay
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

    val journalTitle by viewModel?.journalTitle?.collectAsState()
        ?: remember { mutableStateOf("Write a Title (Optional") }

    val journalDate by viewModel?.journalDate?.collectAsState()
        ?: remember { mutableLongStateOf(System.currentTimeMillis()) }

    var showDatePicker by remember { mutableStateOf(false) }

    val isAnalyzing by viewModel?.isAnalyzing?.collectAsState()
        ?: remember { mutableStateOf(false) }
    val analysisResult by viewModel?.analysisResult?.collectAsState() ?: remember {
        mutableStateOf(
            null
        )
    }

    val context = LocalContext.current
    val shouldFinish by viewModel?.finishActivity?.collectAsState() ?: remember {
        mutableStateOf(
            false
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(shouldFinish) {
        if (shouldFinish) {
            (context as NewJournalActivity).finish()
        }
    }

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
        keyboardController?.show()
    }


    // LaunchedEffect to show the Snackbar or Bottom Sheet when the result changes
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

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = journalDate)

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    viewModel?.updateJournalDate(
                        datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    )
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                viewModel?.onResultShown()
            },
            sheetState = sheetState
        ) {
            analysisResult?.getOrNull()?.let {
                ShowMoodSheet(
                    moodObject = it,
                    onDismiss = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showSheet = false
                            }

                            viewModel?.onResultShown()
                        }
                    }
                )
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .imePadding()
                    .padding(vertical = 10.dp)
            ) {
//                Header()
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable {
                            showDatePicker = true
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Date Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp) // Give the icon a size
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        UtilityMethods.formatMillisToWeeks(journalDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    value = journalTitle,
                    onValueChange = { viewModel?.updateJournalTitle(it) },
                    placeholder = {
                        Text(
                            "Write a Title (Optional)",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp,
                    ),
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                TextField(
                    value = journalBody,
                    onValueChange = { viewModel?.updateJournalBody(it) },
                    placeholder = {
                        Text(
                            "Write anything that's on your mind...",
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            lineHeight = 30.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                        .focusRequester(focusRequester),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 20.sp,
                        lineHeight = 30.sp
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (isAnalyzing) {
                    CircularProgressIndicator()
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, top = 10.dp, start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        FilledIconButton(
//                            onClick = { /* TODO: Handle photo attachment */ },
//                            modifier = Modifier
//                                .shadow(elevation = 8.dp, shape = CircleShape)
//                                .size(48.dp),
//                            colors = IconButtonDefaults.filledIconButtonColors(
//                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
//                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
//                            )
//                        ) {
//                            Icon(
//                                Icons.Filled.AddAPhoto,
//                                contentDescription = "Add Photo",
//                                modifier = Modifier.size(24.dp)
//                            )
//                        }

                        FilledIconButton(
                            onClick = { viewModel?.onSubmitTap() },
                            modifier = Modifier
                                .shadow(elevation = 12.dp, shape = CircleShape)
                                .size(72.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Create Entry",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowMoodSheet(moodObject: AnalyzedMoodObject, onDismiss: () -> Unit) {
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
private fun NewJournalHeader() {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp)
    ) {
        FilledIconButton(
            onClick = {
                (context as NewJournalActivity).finish()
            },
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Icon(
                Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Back Icon",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MindMateTheme {
        JournalScreen(null)
    }
}
