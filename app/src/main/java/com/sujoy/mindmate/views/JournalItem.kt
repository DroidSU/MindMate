package com.sujoy.mindmate.views

import androidx.compose.foundation.Image
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.models.JournalItemModel
import com.sujoy.mindmate.models.Moods
import com.sujoy.mindmate.utils.UtilityMethods

@Composable
fun JournalItem(journalItemModel: JournalItemModel) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_happy),
                    contentDescription = "Mood Icon",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column() {
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
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Text(
                journalItemModel.body,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun JournalItemPreview() {
    val journalItemModel = JournalItemModel(
        id = "",
        title = "Feeling calm",
        body = "This is a sample journal body",
        System.currentTimeMillis(),
        Moods.NEUTRAL
    )
    JournalItem(journalItemModel = journalItemModel)
}