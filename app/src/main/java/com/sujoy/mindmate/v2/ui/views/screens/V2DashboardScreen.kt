package com.sujoy.mindmate.v2.ui.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.v2.data.models.MoodProviderV2
import com.sujoy.mindmate.v2.data.models.MoodV2
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2ColorTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2RadiusTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2SpacingTokens
import com.sujoy.mindmate.v2.ui.designsystem.tokens.V2TypographyTokens

@Composable
fun V2DashboardScreen(
    userName: String,
    currentMood: MoodV2?,
    setCurrentMood: (MoodV2) -> Unit,
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = V2ColorTokens.LavenderAccent)) {
        // 1. BACKGROUND LAYER
        Image(
            painter = painterResource(id = R.drawable.dashboard_illustration),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = V2SpacingTokens.ExtraHuge)
        )

        // 2. FOREGROUND CONTENT LAYER
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // SCROLLABLE TOP SECTION (WEIGHT BASED FOR ADAPTIVITY)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // TOP HEADER ROW
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = V2SpacingTokens.Medium,
                            vertical = V2SpacingTokens.Medium
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu",
                        tint = V2ColorTokens.DeepIndigo,
                        modifier = Modifier.size(24.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "Notifications",
                        tint = V2ColorTokens.DeepIndigo,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // WELCOME TEXT
                Text(
                    text = "Good evening, $userName 👋",
                    style = V2TypographyTokens.BodyMuted.copy(fontSize = 13.sp),
                    modifier = Modifier.padding(horizontal = V2SpacingTokens.Medium)
                )

                // HERO TEXT
                Column(
                    modifier = Modifier.padding(
                        horizontal = V2SpacingTokens.Medium,
                        vertical = V2SpacingTokens.Small
                    )
                ) {
                    Text(
                        text = "You matter.\nAlways.",
                        style = V2TypographyTokens.HeroHeader,
                        lineHeight = 36.sp,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.height(V2SpacingTokens.Small))
                    Text(
                        text = "Let's understand your mind a little better today.",
                        style = V2TypographyTokens.BodyMuted.copy(fontSize = 13.sp)
                    )
                }
            }

            // 3. MOOD SELECTION CARD (Adaptive and Translucent)
            MoodSelectionCard(
                selectedMood = currentMood,
                onMoodSelected = {
                    setCurrentMood(it)
                }
            )
        }

        // 5. FIXED BOTTOM NAVIGATION BAR
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            DashboardBottomNavigation()
        }
    }
}

@Composable
fun MoodSelectionCard(
    selectedMood: MoodV2?,
    onMoodSelected: (MoodV2) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = V2ColorTokens.LavenderLight.copy(alpha = 0.85f), // Translucent to show background illustration
        shape = RoundedCornerShape(topStart = 150.dp, topEnd = 150.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = V2SpacingTokens.Huge)
                .padding(horizontal = V2SpacingTokens.ExtraLarge)
                .navigationBarsPadding() // Adapts to different navigation bar heights
                .padding(bottom = 60.dp), // Extra padding to stay above the BottomNav
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(V2SpacingTokens.Small))
            Text(
                text = "How are you feeling right now?",
                style = V2TypographyTokens.HeadlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = V2TypographyTokens.IntelligenceFontFamily,
                    fontSize = 20.sp
                ),
                color = V2ColorTokens.DeepIndigo
            )
            Spacer(modifier = Modifier.height(V2SpacingTokens.Small))
            Text(
                text = "Your feelings are valid",
                style = V2TypographyTokens.BodyMuted.copy(fontSize = 12.sp),
            )
            Spacer(modifier = Modifier.height(V2SpacingTokens.Medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoodProviderV2.moods.forEach { mood ->
                    MoodItem(
                        mood = mood,
                        isSelected = mood.id == selectedMood?.id,
                        onClick = { onMoodSelected(mood) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(V2SpacingTokens.Medium))

            // Banner card
            BottomBannerCard()
        }
    }
}

@Composable
fun MoodItem(
    mood: MoodV2,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(52.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .then(
                    if (isSelected) Modifier
                        .background(
                            V2ColorTokens.LavenderLight.copy(alpha = 0.9f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(1.5.dp, V2ColorTokens.LavenderAccent, RoundedCornerShape(16.dp))
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = mood.iconResId),
                contentDescription = mood.moodString,
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(V2SpacingTokens.ExtraSmall))
        Text(
            text = mood.moodString,
            style = V2TypographyTokens.LabelLarge.copy(fontSize = 10.sp),
            color = if (isSelected) V2ColorTokens.DeepIndigo else V2ColorTokens.TextMuted,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun BottomBannerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(V2RadiusTokens.Medium),
        colors = CardDefaults.cardColors(containerColor = V2ColorTokens.LavenderLight.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier
                .padding(V2SpacingTokens.Small)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFF6C7CFF),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(V2SpacingTokens.Small))
            Text(
                text = "A small check-in today creates a big shift tomorrow.",
                style = V2TypographyTokens.BodyMedium.copy(
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                ),
                color = V2ColorTokens.TextSecondaryLight,
                modifier = Modifier.weight(1f)
            )
            // Character heart icon
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color(0xFF8B78FF),
                    modifier = Modifier.size(28.dp)
                )
                // Small face details
                Row(modifier = Modifier.padding(bottom = 4.dp)) {
                    Box(modifier = Modifier
                        .size(1.dp)
                        .background(Color.Black))
                    Spacer(modifier = Modifier.width(2.5.dp))
                    Box(modifier = Modifier
                        .size(1.dp)
                        .background(Color.Black))
                }
            }
        }
    }
}

@Composable
fun DashboardBottomNavigation() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 2.dp,
        modifier = Modifier.height(56.dp) // Very compact height
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text("Home", fontSize = 9.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = V2ColorTokens.AiAccent,
                selectedTextColor = V2ColorTokens.AiAccent,
                unselectedIconColor = V2ColorTokens.TextMuted,
                unselectedTextColor = V2ColorTokens.TextMuted,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    Icons.Rounded.EditNote,
                    contentDescription = "Journal",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text("Journal", fontSize = 9.sp) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = V2ColorTokens.TextMuted,
                unselectedTextColor = V2ColorTokens.TextMuted
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    Icons.Rounded.EmojiPeople,
                    contentDescription = "Coach",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text("Coach", fontSize = 9.sp) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = V2ColorTokens.TextMuted,
                unselectedTextColor = V2ColorTokens.TextMuted
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(18.dp)
                )
            },
            label = { Text("Profile", fontSize = 9.sp) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = V2ColorTokens.TextMuted,
                unselectedTextColor = V2ColorTokens.TextMuted
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun V2DashboardPreview() {
    MindMateV2Theme {
        V2DashboardScreen(
            userName = "Sujoy",
            currentMood = MoodProviderV2.getMoodById(4),
            setCurrentMood = {}
        )
    }
}
