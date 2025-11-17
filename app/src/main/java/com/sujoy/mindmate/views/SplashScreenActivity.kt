package com.sujoy.mindmate.views

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
import androidx.compose.material3.Scaffold
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.sujoy.mindmate.R
import com.sujoy.mindmate.ui.theme.MindMateTheme
import kotlinx.coroutines.delay

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                Scaffold() { innerPadding ->
                    App(innerPadding)
                }
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
        delay(3000) // Wait for the animation to finish + a little extra
        val auth = Firebase.auth
//        if(auth.currentUser == null){
//            val intent = Intent(context, AuthenticationActivity::class.java)
//            context.startActivity(intent)
//            (context as ComponentActivity).finish()
//        }
//        else{
//            val intent = Intent(context, MainActivity::class.java)
//            context.startActivity(intent)
//            (context as ComponentActivity).finish()
//        }

        val intent = Intent(context, OnboardingScreen::class.java)
        context.startActivity(intent)
        (context as ComponentActivity).finish()
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
