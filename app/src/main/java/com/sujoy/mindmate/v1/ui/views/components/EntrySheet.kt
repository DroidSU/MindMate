package com.sujoy.mindmate.v1.ui.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sujoy.mindmate.v1.ui.theme.MindMateTheme

@Composable
fun EntrySheet(
    text: String,
    onTextChange: (String) -> Unit,
    moodColor: Color
) {
    val spacing = MindMateTheme.spacing
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.large
            )
            .padding(spacing.lg)
    ) {
        Column {
            TextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = {
                    Text(
                        "How are you truly feeling?",
                        style = MaterialTheme.typography.titleLarge,
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
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal,
                    lineHeight = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
