package com.sujoy.mindmate.v1.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.data.models.AppUiState
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import com.sujoy.mindmate.v1.utils.ConstantsManager
import com.sujoy.mindmate.v1.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    val databaseRepository: DatabaseRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<AppUiState>(AppUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _journalItemList: MutableStateFlow<List<JournalItemDBModel>> =
        MutableStateFlow(emptyList())
    val journalItemList = _journalItemList.asStateFlow()

    init {
        getJournalItems()
    }


    fun getJournalItems() {
        _uiState.value = AppUiState.Loading
        viewModelScope.launch {
            try {
                databaseRepository.getFirst10JournalItems().collect {
                    _journalItemList.value = it

                    _uiState.value = AppUiState.Success
                }
            } catch (ex: Exception) {
                Log.e(ConstantsManager.APP_TAG, "getJournalItems: ${ex.message.toString()}")
            }
        }
    }

    fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            databaseRepository.clearAllData()
            dataStoreManager.clearData()
            onComplete()
        }
    }
}