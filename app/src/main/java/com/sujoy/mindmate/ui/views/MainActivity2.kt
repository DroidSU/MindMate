package com.sujoy.mindmate.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.ui.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateTheme {
                val context = LocalContext.current

                val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()

                LaunchedEffect(isOnboardingCompleted) {
                    when (isOnboardingCompleted) {
                        true -> {
                            context.startActivity(Intent(context, TimelineActivity::class.java))
                            (context as ComponentActivity).finish()
                        }

                        false -> {
                            context.startActivity(Intent(context, TimelineActivity::class.java))
                            (context as ComponentActivity).finish()
                        }

                        null -> {
                            // Still loading status
                        }
                    }
                }
            }
        }
    }
}