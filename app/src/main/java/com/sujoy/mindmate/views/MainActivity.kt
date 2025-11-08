package com.sujoy.mindmate.views

import android.content.Intent
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.models.JournalItemModel
import com.sujoy.mindmate.models.Moods
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.vm.MainActivityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen(viewModel: MainActivityViewModel = viewModel()) {
    val isFABClicked by viewModel.isFABClicked.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(isFABClicked) {
        if (isFABClicked) {
            viewModel.onActivitySwitch()
            context.startActivity(Intent(context, NewJournalActivity::class.java))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent, // This makes the Scaffold's background see-through
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.onFABClick()
                    },
                    modifier = Modifier.clip(CircleShape),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add new journal entry",
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center // Position it in the center
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
            ) {
                Header()
                Spacer(modifier = Modifier.height(8.dp))
                JournalList()
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("MindMate", style = MaterialTheme.typography.headlineLarge)
            Text(
                "Your calm space for reflection",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light
            )
        }
        Icon(Icons.Filled.AccountCircle, contentDescription = "Account", Modifier.size(50.dp))
    }
}

@Composable
private fun JournalList() {
    LazyColumn {
        items(10) {

            // REMOVE THIS SECTION AFTER ADDING ACTUAL LOGIC
            val journalItemModel = JournalItemModel(
                id = "",
                title = "Feeling calm",
                body = "This is sample journal body $it",
                System.currentTimeMillis(),
                Moods.NEUTRAL
            )
            JournalItem(journalItemModel = journalItemModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MindMateTheme {
        MainScreen()
    }
}
