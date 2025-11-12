package com.sujoy.mindmate.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.sujoy.mindmate.ui.theme.HappyColor
import com.sujoy.mindmate.ui.theme.MotivatedColor
import com.sujoy.mindmate.ui.theme.NeutralColor
import com.sujoy.mindmate.ui.theme.SadColor
import com.sujoy.mindmate.utils.ConstantsManager
import com.sujoy.mindmate.utils.UtilityMethods

@Composable
fun JournalItem(journalItemModel: JournalItemModel) {

    val (moodIcon, cardColor) = when (journalItemModel.sentiment) {
        ConstantsManager.HAPPY -> R.drawable.ic_happy to HappyColor
        ConstantsManager.SAD -> R.drawable.ic_sad to SadColor
        ConstantsManager.NEUTRAL -> R.drawable.ic_neutral to NeutralColor
        ConstantsManager.ANGRY -> R.drawable.ic_angry to AngryColor
        ConstantsManager.MOTIVATED -> R.drawable.ic_happy to MotivatedColor
        ConstantsManager.ANXIOUS -> R.drawable.ic_happy to AnxiousColor
        else -> R.drawable.ic_neutral to NeutralColor
    }

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
                    modifier = Modifier.size(32.dp)
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
                style = MaterialTheme.typography.bodyMedium,
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

@Preview(showBackground = true)
@Composable
fun JournalItemPreview() {
    val journalItemModel = JournalItemModel(
        title = "Feeling calm",
        body = "This is a sample journal body",
        date = System.currentTimeMillis(),
        sentiment = ConstantsManager.SAD
    )
    JournalItem(journalItemModel = journalItemModel)
}
