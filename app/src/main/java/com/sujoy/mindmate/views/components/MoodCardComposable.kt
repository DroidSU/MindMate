package com.sujoy.mindmate.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.models.MoodDataModel
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.utils.ConstantsManager

@Composable
fun MoodCard(mood: MoodDataModel, isSelected: Boolean, onClick: () -> Unit) {
    val semantics = if (isSelected) "Selected" else "Not selected"
    Card(
        modifier = Modifier
            .semantics { contentDescription = "${mood.name}, ${mood.description}, $semantics" }
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = mood.emoji, fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mood.name,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mood.description,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                color = if (isSelected) MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.8f)
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodCardPreview() {
    MindMateTheme {
        MoodCard(
            mood = MoodDataModel(
                name = ConstantsManager.HAPPY,
                emoji = "🙂",
                description = "Feeling Happy"
            ),
            isSelected = true,
            onClick = {}
        )
    }
}
