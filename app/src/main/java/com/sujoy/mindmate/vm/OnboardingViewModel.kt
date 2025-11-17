package com.sujoy.mindmate.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep

    fun nextStep() {
        if (_currentStep.value < 2) { // We have 3 steps (0, 1, 2)
            _currentStep.value++
        }
    }

    fun previousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value--
        }
    }
}