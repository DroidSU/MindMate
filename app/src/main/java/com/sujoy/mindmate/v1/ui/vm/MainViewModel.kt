package com.sujoy.mindmate.v1.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.v1.utils.DataStoreManager
import com.sujoy.mindmate.v1.utils.UtilityMethods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)
    val isOnboardingCompleted = _isOnboardingCompleted.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    init {
        getAllData()
    }

    private fun getAllData() {
        viewModelScope.launch {
            _username.value = dataStoreManager.getUsername()

            if (_username.value.isEmpty()) {
                val newUsername = UtilityMethods.generateUniqueUsername("user")
                dataStoreManager.saveUsername(newUsername)
                _username.value = newUsername
            }

            _isOnboardingCompleted.value = dataStoreManager.isOnboardingCompleted()
        }
    }
}
