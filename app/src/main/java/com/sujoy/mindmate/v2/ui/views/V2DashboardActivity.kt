package com.sujoy.mindmate.v2.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import com.sujoy.mindmate.v2.ui.views.screens.V2DashboardScreen
import com.sujoy.mindmate.v2.ui.vm.DashboardV2ViewModel

class V2DashboardActivity : ComponentActivity() {

    private val viewmodel: DashboardV2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMateV2Theme {
                V2DashboardScreen()
            }
        }
    }
}