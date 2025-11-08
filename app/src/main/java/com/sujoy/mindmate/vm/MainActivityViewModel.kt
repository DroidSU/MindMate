package com.sujoy.mindmate.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel : ViewModel() {
    private val _isFABClicked = MutableStateFlow(false)
    val isFABClicked: StateFlow<Boolean> = _isFABClicked.asStateFlow()

    fun onFABClick() {
        _isFABClicked.value = true
    }

    fun onActivitySwitch() {
        _isFABClicked.value = false
    }
}