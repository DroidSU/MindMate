package com.sujoy.mindmate.v1.data.models

sealed class AppUiState {
    object Idle : AppUiState()
    object Loading : AppUiState()
    object Success : AppUiState()
    data class Error(val message: String) : AppUiState()
}