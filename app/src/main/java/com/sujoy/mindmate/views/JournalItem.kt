package com.sujoy.mindmate.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.models.JournalItemModel
import com.sujoy.mindmate.ui.theme.AngryColor
import com.sujoy.mindmate.ui.theme.AnxiousColor
import com.sujoy.mindmate.ui.theme.DeleteColor
import com.sujoy.mindmate.ui.theme.HappyColor
import com.sujoy.mindmate.ui.theme.MotivatedColor
import com.sujoy.mindmate.ui.theme.NeutralColor
import com.sujoy.mindmate.ui.theme.SadColor
import com.sujoy.mindmate.utils.ConstantsManager
import com.sujoy.mindmate.utils.UtilityMethods
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalItem(journalItemModel: JournalItemModel, onItemSwiped: () -> Unit) {
    var showItem by remember { mutableStateOf(true) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                showItem = false
                true
            } else {
                false
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.8f }
    )

    val (moodIcon, cardColor) = when (journalItemModel.sentiment) {
        ConstantsManager.HAPPY -> R.drawable.ic_happy to HappyColor
        ConstantsManager.SAD -> R.drawable.ic_sad to SadColor
        ConstantsManager.NEUTRAL -> R.drawable.ic_neutral to NeutralColor
        ConstantsManager.ANGRY -> R.drawable.ic_angry to AngryColor
        ConstantsManager.MOTIVATED -> R.drawable.ic_motivated to MotivatedColor
        ConstantsManager.ANXIOUS -> R.drawable.ic_anxious to AnxiousColor
        else -> R.drawable.ic_neutral to NeutralColor
    }

    AnimatedVisibility(
        visible = showItem,
        exit = shrinkVertically(animationSpec = tween(durationMillis = 500)) + fadeOut(
            animationSpec = tween(
                durationMillis = 500
            )
        )
    ) {
        LaunchedEffect(showItem) {
            if (!showItem) {
                delay(500)
                onItemSwiped()
            }
        }

        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier.padding(vertical = 2.dp),
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                SwipedViewComposable(dismissState)
            }
        ) {
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(moodIcon),
                            contentDescription = "Mood Icon",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Column {
                            Text(
                                journalItemModel.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                UtilityMethods.formatDate(journalItemModel.date),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                    Text(
                        journalItemModel.body,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SwipedViewComposable(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> DeleteColor
        else -> Color.Transparent
    }

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            color.copy(alpha = 0.5f),
            color.copy(alpha = 1.0f)
        )
    )

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Make card transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
                .padding(horizontal = 30.dp),
            contentAlignment = Alignment.CenterEnd // Align content (the icon) to the end
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete Icon",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JournalItemPreview() {
    val journalItemModel = JournalItemModel(
        title = "Feeling calm",
        body = "This is a sample journal body",
        date = System.currentTimeMillis(),
        sentiment = ConstantsManager.NEUTRAL
    )
    JournalItem(journalItemModel = journalItemModel, onItemSwiped = {})
}
