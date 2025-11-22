package com.sujoy.mindmate.views.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.models.MoodLog
import com.sujoy.mindmate.ui.theme.AngryDarkColorScheme
import com.sujoy.mindmate.ui.theme.AngryLightColorScheme
import com.sujoy.mindmate.ui.theme.AnxiousDarkColorScheme
import com.sujoy.mindmate.ui.theme.AnxiousLightColorScheme
import com.sujoy.mindmate.ui.theme.CalmDarkColorScheme
import com.sujoy.mindmate.ui.theme.CalmLightColorScheme
import com.sujoy.mindmate.ui.theme.DarkColorScheme
import com.sujoy.mindmate.ui.theme.HappyDarkColorScheme
import com.sujoy.mindmate.ui.theme.HappyLightColorScheme
import com.sujoy.mindmate.ui.theme.LightColorScheme
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.theme.SadDarkColorScheme
import com.sujoy.mindmate.ui.theme.SadLightColorScheme


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentColorScheme by remember {
                mutableStateOf(
                    LightColorScheme
                )
            }

            val isDarkTheme = isSystemInDarkTheme()

            LaunchedEffect(isDarkTheme) {
                currentColorScheme = if (isDarkTheme) {
                    DarkColorScheme
                } else {
                    LightColorScheme
                }
            }

            MindMateTheme(darkTheme = isDarkTheme, colorScheme = currentColorScheme) {
                HomeScreen("Sujoy", onThemeChange = { newScheme ->
                    currentColorScheme = newScheme
                })
            }
        }
    }
}

@Composable
fun HomeScreen(name: String, onThemeChange: (ColorScheme) -> Unit) {
    var selectedNavItem by remember { mutableStateOf(0) }
    // Dummy data for previewing the timeline design
    // To be changed later
    val moodLogs = listOf(
        MoodLog(
            "Happy",
            "😊",
            "Today, 9:15 AM",
            "Felt great after my morning walk.",
            Color(0xFF4CAF50)
        ),
        MoodLog(
            "Tired",
            "😴",
            "Yesterday, 10:30 PM",
            "Long day at work. Need some rest.",
            Color(0xFF9E9E9E)
        ),
        MoodLog(
            "Anxious",
            "😟",
            "Yesterday, 1:20 PM",
            "Stressed about the upcoming deadline.",
            Color(0xFFFFC107)
        ),
        MoodLog("Calm", "😌", "Yesterday, 8:00 AM", null, Color(0xFF00BCD4))
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopAppBar() },
        bottomBar = { HomeBottomNavigation(selectedNavItem) { selectedNavItem = it } },
//        containerColor = Color.Transparent
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Make screen scrollable
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            GreetingSection(name = name)
            Spacer(modifier = Modifier.height(24.dp))
            MoodSelector(onThemeChange = onThemeChange)
            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "What's on your mind?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuickJournalCard()

            Spacer(modifier = Modifier.height(32.dp))

            AtRiskHabitCard()

            Spacer(modifier = Modifier.height(32.dp))

            RecentActivityTimeline(moodLogs)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "MindMate",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                // Change 2: Adapted text color for gradient background
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { /* TODO: Handle avatar click */ }
            )
            Spacer(modifier = Modifier.width(16.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun GreetingSection(name: String) {
    Column {
        Text(
            text = "Good morning, $name.",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "How are you feeling today?",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MoodSelector(onThemeChange: (ColorScheme) -> Unit) {
    val moods = listOf("😊 Happy", "😌 Calm", "🔥 Angry", "😢 Sad", "⚡️ Anxious")
    val isDarkTheme = isSystemInDarkTheme()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(moods) { mood ->
            SuggestionChip(
                onClick = {
                    when {
                        mood.contains("Happy") -> {
                            onThemeChange(if (isDarkTheme) HappyDarkColorScheme else HappyLightColorScheme)
                        }

                        mood.contains("Calm") -> {
                            onThemeChange(if (isDarkTheme) CalmDarkColorScheme else CalmLightColorScheme)
                        }

                        mood.contains("Angry") -> {
                            onThemeChange(if (isDarkTheme) AngryDarkColorScheme else AngryLightColorScheme)
                        }

                        mood.contains("Sad") -> {
                            onThemeChange(if (isDarkTheme) SadDarkColorScheme else SadLightColorScheme)
                        }

                        mood.contains("Anxious") -> {
                            onThemeChange(if (isDarkTheme) AnxiousDarkColorScheme else AnxiousLightColorScheme)
                        }

                        else -> {
                            onThemeChange(if (isDarkTheme) DarkColorScheme else LightColorScheme)
                        }
                    }
                },
                label = { Text(mood) },
                shape = CircleShape,
                // Change 2: Adapted chip colors
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = null
            )
        }
        item {
            SuggestionChip(
                onClick = { /* TODO: Open detailed mood modal */ },
                label = { Text("More") },
                icon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = CircleShape,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = null
            )
        }
    }
}

@Composable
fun QuickJournalCard() {
    var journalText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = journalText,
        onValueChange = { journalText = it },
        modifier = Modifier
            .fillMaxWidth()
            // 1. Added a shadow to lift the component
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp)),
        placeholder = { Text("One line: what’s on your mind?") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Mic,
                contentDescription = "Record voice note",
                modifier = Modifier.clickable { /* TODO: Implement voice input */ }
            )
        },
        shape = RoundedCornerShape(16.dp),
        // 2. Updated colors for better visibility and theme adaptation
        colors = OutlinedTextFieldDefaults.colors(
            // Use a solid container color that adapts to light/dark mode
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            // Use onSurface for text and icons for proper contrast
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            // Use Primary color for the focused border to draw attention
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Transparent, // No border when not focused for a cleaner look
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        maxLines = 1
    )
}


@Composable
fun AtRiskHabitCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = MaterialTheme.colorScheme.tertiary
            ),
        shape = RoundedCornerShape(24.dp),
        // Change 2: Use a semi-transparent surface for a frosted glass effect
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "At Risk: Workout",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "You usually skip this when you feel tired.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Change 1: Improved Button Layout
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { /* TODO: Handle stretch action */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Do 2-min stretch")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { /* TODO: Snooze habit */ }) {
                        Text("Snooze")
                    }
                    TextButton(onClick = { /* TODO: Mark as done */ }) {
                        Text("Mark as done")
                    }
                }
            }
        }
    }
}

@Composable
fun HomeBottomNavigation(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        "Home" to Icons.Default.Home,
        "Insights" to Icons.Default.Analytics,
        "History" to Icons.Default.History,
        "Settings" to Icons.Default.Settings
    )

    NavigationBar(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp)),
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
        tonalElevation = 0.dp // Elevation is handled by the shadow modifier
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.second,
                        contentDescription = item.first,
                        modifier = if (index == selectedItem) Modifier.size(32.dp) else Modifier.size(
                            24.dp
                        )
                    )
                },
                label = { Text(item.first) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}

@Composable
fun RecentActivityTimeline(moodLogs: List<MoodLog>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Recent Timeline",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                moodLogs.forEachIndexed { index, log ->
                    TimelineItem(log, isLastItem = index == moodLogs.size - 1)
                }
            }
        }
    }
}

@Composable
fun TimelineItem(log: MoodLog, isLastItem: Boolean) {
    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // The colored bullet point
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(log.color, CircleShape)
            )
            // The vertical line connecting the bullet points
            if (!isLastItem) {
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // This Column holds the content for the mood log
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (!isLastItem) 20.dp else 0.dp) // Space between items
        ) {
            // Mood and Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${log.emoji} ${log.mood}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "• ${log.time}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Journal Entry (only shown if it exists)
            log.journalEntry?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HomeScreenPreview() {
    MindMateTheme {
        HomeScreen("Sujoy", onThemeChange = {})
    }
}
