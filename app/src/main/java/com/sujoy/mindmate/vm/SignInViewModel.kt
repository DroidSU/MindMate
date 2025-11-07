package com.sujoy.mindmate.vm

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.sujoy.mindmate.repositories.AuthRepository
import com.sujoy.mindmate.repositories.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val authRepository : AuthRepository = AuthRepositoryImpl()

    // --- UI State ---
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    // --- Authentication State ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    // --- Validation State ---
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    init {
        if(authRepository.currentUser != null) {
            _isAuthenticated.value = true
        }
    }


    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        if (_emailError.value != null) { _emailError.value = null }
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        if (_passwordError.value != null) { _passwordError.value = null }
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        if (_passwordError.value != null) { _passwordError.value = null }
    }

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
        // Clear errors when switching tabs
        _emailError.value = null
        _passwordError.value = null
        _authError.value = null
    }


    fun onSignInClick() {
        if (!validateEmail()) return

        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.signInWithEmail(email.value, password.value)

            result.onSuccess {
                _isAuthenticated.value = true
            }.onFailure { exception ->
                _authError.value = when (exception) {
                    is FirebaseAuthInvalidUserException -> "No account found with this email."
                    is FirebaseAuthInvalidCredentialsException -> "Invalid password. Please try again."
                    else -> "Sign-in failed: ${exception.localizedMessage}"
                }
            }
            _isLoading.value = false
        }
    }

    fun onSignUpClick() {
        if (!validateEmail() || !validatePasswords()) return

        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.signUpWithEmail(email.value, password.value)

            result.onSuccess {
                _isAuthenticated.value = true
            }.onFailure { exception ->
                _authError.value = when (exception) {
                    is FirebaseAuthUserCollisionException -> "An account already exists with this email."
                    else -> "Sign-up failed: ${exception.localizedMessage}"
                }
            }
            _isLoading.value = false
        }
    }

    private fun validateEmail(): Boolean {
        val currentEmail = _email.value
        if (currentEmail.isBlank()) {
            _emailError.value = "Email cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            _emailError.value = "Invalid email format"
            return false
        }
        _emailError.value = null
        return true
    }

    private fun validatePasswords(): Boolean {
        val currentPassword = _password.value
        val currentConfirmPassword = _confirmPassword.value
        if (currentPassword.length < 6) {
            _passwordError.value = "Password must be at least 6 characters"
            return false
        }
        if (currentPassword != currentConfirmPassword) {
            _passwordError.value = "Passwords do not match"
            return false
        }
        _passwordError.value = null
        return true
    }
}
