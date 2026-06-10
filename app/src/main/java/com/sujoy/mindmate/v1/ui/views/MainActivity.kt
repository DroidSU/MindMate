package com.sujoy.mindmate.v1.ui.views

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
import com.sujoy.mindmate.v1.ui.theme.MindMateTheme
import com.sujoy.mindmate.v1.ui.vm.MainViewModel
import com.sujoy.mindmate.v1.utils.SyncManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var syncManager: SyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        syncManager.startSync()
        setContent {
            MindMateTheme {
                val context = LocalContext.current

                val username by viewModel.username.collectAsState()
                val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()

                LaunchedEffect(isOnboardingCompleted, username) {
                    // Wait until isOnboardingCompleted state is determined (not null)
                    // and a username exists (handled by ViewModel auto-generating it)
                    if (isOnboardingCompleted == null || username.isEmpty()) return@LaunchedEffect

                    val targetActivity = if (isOnboardingCompleted == true) {
                        HomeActivity::class.java
                    } else {
                        com.sujoy.mindmate.v2.ui.views.V2AuthenticationActivity::class.java
                    }

                    targetActivity.let {
                        context.startActivity(Intent(context, it))
                        (context as? ComponentActivity)?.finish()
                    }
                }
            }
        }
    }
}
