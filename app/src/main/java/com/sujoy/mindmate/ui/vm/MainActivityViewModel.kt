package com.sujoy.mindmate.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.data.database.JournalDAO
import com.sujoy.mindmate.data.models.JournalItemModel
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val journalDAO: JournalDAO,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _isFABClicked = MutableStateFlow(false)
    val isFABClicked: StateFlow<Boolean> = _isFABClicked.asStateFlow()

    val allJournals: StateFlow<List<JournalItemModel>> = journalDAO.getAllJournals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onFABClick() {
        _isFABClicked.value = true
    }

    fun onActivitySwitch() {
        _isFABClicked.value = false
    }

    fun deleteJournal(journalItem: JournalItemModel) {
        viewModelScope.launch {
            databaseRepository.deleteJournal(journalItem.id)
        }
    }
}
