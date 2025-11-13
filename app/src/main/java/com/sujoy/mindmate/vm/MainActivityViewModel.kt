package com.sujoy.mindmate.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.db.MindMateDatabase
import com.sujoy.mindmate.models.JournalItemModel
import com.sujoy.mindmate.repositories.DatabaseRepository
import com.sujoy.mindmate.repositories.DatabaseRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val journalDAO = MindMateDatabase.getDatabase(application).journalDao()
    private val databaseRepository: DatabaseRepository = DatabaseRepositoryImpl(journalDAO)

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

//    fun getAllJournals() {
//        viewModelScope.launch {
//            databaseRepository.getAllJournals()
//        }
//    }

    fun deleteJournal(journalItem: JournalItemModel) {
        viewModelScope.launch {
            databaseRepository.deleteJournal(journalItem.id)
        }
    }
}