package com.sujoy.mindmate.v2.ui.views

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
import com.sujoy.mindmate.v1.data.models.AppUiState
import com.sujoy.mindmate.v1.ui.views.MainActivity
import com.sujoy.mindmate.v1.ui.vm.AuthEvent
import com.sujoy.mindmate.v1.ui.vm.AuthViewModel
import com.sujoy.mindmate.v2.ui.designsystem.theme.MindMateV2Theme
import com.sujoy.mindmate.v2.ui.views.screens.V2AuthScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class V2AuthenticationActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState(initial = AppUiState.Idle)

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is AuthEvent.LaunchGoogleSignIn -> {
                            launchGoogleSignIn()
                        }

                        is AuthEvent.NavigateToHome -> {
//                             For now, navigating back to v1 MainActivity which handles routing, or we could navigate to a V2HomeActivity in the future.
                            startActivity(
                                Intent(
                                    this@V2AuthenticationActivity,
                                    MainActivity::class.java,
                                )
                            )
                            finish()
                        }
                    }
                }
            }

            MindMateV2Theme {
                V2AuthScreen(
                    uiState = uiState,
                    onGoogleSignInClick = viewModel::onGoogleSignInClick
                )
            }
        }
    }

    private fun launchGoogleSignIn() {
        val credentialManager = CredentialManager.create(this)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts = false)
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
                    context = this@V2AuthenticationActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("V2AuthActivity", "Error getting credential", e)
                viewModel.setIdle()
            }
        }
    }

    private fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        val credential = result.credential
        if (credential is GoogleIdTokenCredential) {
            val idToken = credential.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            viewModel.signInWithCredential(firebaseCredential)
        } else {
            Log.e("V2AuthActivity", "Received unexpected credential type: ${credential.type}")
            viewModel.setIdle()
        }
    }
}
