package com.sujoy.mindmate.v2.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import com.sujoy.mindmate.v2.ui.views.screens.V2DashboardScreen
import com.sujoy.mindmate.v2.ui.vm.DashboardV2ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class V2DashboardActivity : ComponentActivity() {

    private val viewmodel: DashboardV2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateV2Theme {
                val username by viewmodel.userName.collectAsState()
                val currentMood by viewmodel.currentMood.collectAsState()

                V2DashboardScreen(
                    userName = username,
                    currentMood = currentMood,
                    setCurrentMood = {
                        viewmodel.storeSelectedMood(it)
                    }
                )
            }
        }
    }
}