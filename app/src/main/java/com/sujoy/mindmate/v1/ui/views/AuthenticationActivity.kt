package com.sujoy.mindmate.v1.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.sujoy.mindmate.BuildConfig
import com.sujoy.mindmate.v1.ui.theme.MindMateTheme
import com.sujoy.mindmate.v1.ui.views.screens.AuthScreen
import com.sujoy.mindmate.v1.ui.vm.AuthEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    private val viewModel: com.sujoy.mindmate.v1.ui.vm.AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is AuthEvent.LaunchGoogleSignIn -> {
                            launchGoogleSignIn()
                        }

                        is AuthEvent.NavigateToHome -> {
                            startActivity(
                                Intent(
                                    this@AuthenticationActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }
            }

            MindMateTheme {
                AuthScreen(
                    uiState = uiState,
                    onGoogleSignInClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
//                        viewModel.onGoogleSignInClick()
                    }
                )
            }
        }
    }

    private fun launchGoogleSignIn() {
        val credentialManager = CredentialManager.create(this)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@AuthenticationActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("AuthActivity", "Error getting credential", e)
                viewModel.setIdle()
            }
        }
    }

    private fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        val credential = result.credential
        if (credential is GoogleIdTokenCredential) {
            val googleIdTokenCredential = credential
            val idToken = googleIdTokenCredential.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            viewModel.signInWithCredential(firebaseCredential)
        } else {
            Log.e("AuthActivity", "Received unexpected credential type: ${credential.type}")
            viewModel.setIdle()
        }
    }
}