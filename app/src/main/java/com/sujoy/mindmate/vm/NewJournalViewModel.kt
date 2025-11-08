package com.sujoy.mindmate.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewJournalViewModel : ViewModel() {
    private val _journalBody = MutableStateFlow("")
    val journalBody: StateFlow<String> = _journalBody.asStateFlow()


    fun updateJournalBody(newBody: String) {
        _journalBody.value = newBody
    }
}