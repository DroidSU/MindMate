package com.sujoy.mindmate.ui.views.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujoy.mindmate.R
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.utils.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {

            }
        }
    }
}

@Composable
fun App(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var startAnimation by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (startAnimation) 200.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 3000 // Adjust the duration as you like
        ),
        label = "sizeAnimation"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(3000)

        if (DataStoreManager(context).isOnboardingCompleted()) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            (context as ComponentActivity).finish()
        } else {
            val intent = Intent(context, OnboardingActivity::class.java)
            context.startActivity(intent)
            (context as ComponentActivity).finish()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.mindmate_transparent),
            contentDescription = "",
            modifier = Modifier
                .size(size)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App(innerPadding = PaddingValues(10.dp))
}
