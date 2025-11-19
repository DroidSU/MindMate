package com.sujoy.mindmate.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.utils.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreManager = DataStoreManager(application)

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _selectedHabits = MutableStateFlow<Set<String>>(emptySet())
    val selectedHabits: StateFlow<Set<String>> = _selectedHabits.asStateFlow()

    private val _selectedMoods = MutableStateFlow<Set<String>>(emptySet())
    val selectedMoods: StateFlow<Set<String>> = _selectedMoods.asStateFlow()

    fun nextStep() {
        if (_currentStep.value < 3) { // We have 4 steps (0, 1, 2, 3)
            _currentStep.value++
        }
    }

    fun previousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value--
        }
    }

    fun setCurrentStep(step: Int) {
        if (step in 0..3) {
            _currentStep.value = step
        }
    }

    fun addHabit(habit: String) {
        _selectedHabits.value += habit
    }

    fun removeHabit(habit: String) {
        _selectedHabits.value -= habit
    }

    fun addMood(mood: String) {
        _selectedMoods.value += mood
    }

    fun removeMood(mood: String) {
        _selectedMoods.value -= mood
    }

    fun saveSelections() {
        viewModelScope.launch {
            dataStoreManager.saveOnboardingSelections(
                selectedHabits = selectedHabits.value,
                selectedMoods = selectedMoods.value,
                reminderType = 0,
                reminderTime = 0
            )
        }
    }

    fun getSelectedHabits() {
        viewModelScope.launch {
            _selectedHabits.value = dataStoreManager.getSelectedHabits()
        }
    }

    fun getSelectedMoods() {
        viewModelScope.launch {
            _selectedMoods.value = dataStoreManager.getSelectedMoods()
        }
    }
}