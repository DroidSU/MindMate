package com.sujoy.mindmate.v1.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.v1.utils.ConstantsManager
import com.sujoy.mindmate.v1.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(AppUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AuthEvent>()
    val events: SharedFlow<AuthEvent> = _events.asSharedFlow()

    private val auth = FirebaseAuth.getInstance()

    fun onGoogleSignInClick() {
        _uiState.value = AppUiState.Loading
        viewModelScope.launch {
            _events.emit(AuthEvent.LaunchGoogleSignIn)
        }
    }

    fun signInWithCredential(credential: AuthCredential) {
        _uiState.value = AppUiState.Loading
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential).await()
                _uiState.value = AppUiState.Success
                dataStoreManager.setOnboardingCompleted()
                _events.emit(AuthEvent.NavigateToHome)
            } catch (e: Exception) {
                Log.e(ConstantsManager.APP_TAG, "Sign in with credential failed", e)
                _uiState.value = AppUiState.Idle
            }
        }
    }

    fun setError(message: String) {
        _uiState.value = AppUiState.Error(message)
    }

    fun setIdle() {
        _uiState.value = AppUiState.Idle
    }

}

sealed class AuthEvent {
    object LaunchGoogleSignIn : AuthEvent()
    object NavigateToHome : AuthEvent()
}