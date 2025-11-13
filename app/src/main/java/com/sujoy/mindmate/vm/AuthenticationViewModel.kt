package com.sujoy.mindmate.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.sujoy.mindmate.repositories.MindMateApiRepoImpl
import com.sujoy.mindmate.utils.ValidationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {
    private val mindMateApiRepository = MindMateApiRepoImpl()

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

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError.asStateFlow()

    init {
        if (mindMateApiRepository.currentUser != null) {
            _isAuthenticated.value = true
        }
    }


    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        if (_emailError.value != null) {
            _emailError.value = null
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        if (_passwordError.value != null) {
            _passwordError.value = null
        }
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        if (_passwordError.value != null) {
            _passwordError.value = null
        }
    }

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
        // Clear errors when switching tabs
        _emailError.value = null
        _passwordError.value = null
        _authError.value = null
    }


    fun onSignInClick() {
        val emailValidationResult = ValidationManager().validateEmail(_email.value)
        val passwordValidationResult =
            ValidationManager().validatePassword(_password.value, _password.value)

        when {
            emailValidationResult != 0 || passwordValidationResult != 0 -> {
                if (emailValidationResult == 1) {
                    _emailError.value = "Invalid Email"
                }

                if (emailValidationResult == 2) {
                    _emailError.value = "Email cannot be empty"
                }

                if (passwordValidationResult == 1) {
                    _passwordError.value = "Password cannot be empty"
                }

                if (passwordValidationResult == 2) {
                    _passwordError.value = "Password must be at least 6 characters"
                }

                if (passwordValidationResult == 3) {
                    _passwordError.value = "Passwords do not match"
                }

                return
            }
            else -> {
                _emailError.value = ""
                _passwordError.value = ""

                viewModelScope.launch {
                    _isLoading.value = true
                    _authError.value = null
                    val result = mindMateApiRepository.signInWithEmail(email.value, password.value)

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
        }
    }

    fun onSignUpClick() {
        val emailValidationResult = ValidationManager().validateEmail(_email.value)
        val passwordValidationResult =
            ValidationManager().validatePassword(_password.value, _password.value)

        when {
            emailValidationResult != 0 || passwordValidationResult != 0 -> {
                if (emailValidationResult == 1) {
                    _emailError.value = "Invalid Email"
                }

                if (emailValidationResult == 2) {
                    _emailError.value = "Email cannot be empty"
                }

                if (passwordValidationResult == 1) {
                    _passwordError.value = "Password cannot be empty"
                }

                if (passwordValidationResult == 2) {
                    _passwordError.value = "Password must be at least 6 characters"
                }

                if (passwordValidationResult == 3) {
                    _passwordError.value = "Passwords do not match"
                }

                return
            }
            else -> {
                _emailError.value = null
                _passwordError.value = null
                _confirmPasswordError.value = null

                viewModelScope.launch {
                    _isLoading.value = true
                    _authError.value = null
                    val result = mindMateApiRepository.signUpWithEmail(email.value, password.value)

                    result.onSuccess {
                        _isAuthenticated.value = true
                    }.onFailure { exception ->
                        _authError.value = when (exception) {
                            is FirebaseAuthUserCollisionException -> "An account already exists with this email."
                            else -> "Sign-up failed: ${exception.localizedMessage}"
                        }
                        Log.d("MindMate", "onSignUpClick: ${exception.localizedMessage}")
                    }
                    _isLoading.value = false
                }
            }
        }
    }
}
