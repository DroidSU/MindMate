package com.sujoy.mindmate.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.db.MindMateDatabase
import com.sujoy.mindmate.models.AnalyzedMoodObject
import com.sujoy.mindmate.models.JournalItemModel
import com.sujoy.mindmate.repositories.DatabaseRepository
import com.sujoy.mindmate.repositories.DatabaseRepositoryImpl
import com.sujoy.mindmate.repositories.MindMateApiRepoImpl
import com.sujoy.mindmate.utils.ConstantsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewJournalViewModel(application: Application) : AndroidViewModel(application) {
    private val mindMateApiRepository = MindMateApiRepoImpl()
    private val journalDAO = MindMateDatabase.getDatabase(application).journalDao()
    private val databaseRepository: DatabaseRepository = DatabaseRepositoryImpl(journalDAO)

    private val _journalTitle = MutableStateFlow("")
    val journalTitle: StateFlow<String> = _journalTitle.asStateFlow()

    private val _journalBody = MutableStateFlow("")
    val journalBody: StateFlow<String> = _journalBody.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analysisResult = MutableStateFlow<Result<AnalyzedMoodObject>?>(null)
    val analysisResult: StateFlow<Result<AnalyzedMoodObject>?> = _analysisResult.asStateFlow()

    fun updateJournalTitle(newTitle: String) {
        _journalTitle.value = newTitle
    }

    fun updateJournalBody(newBody: String) {
        _journalBody.value = newBody
    }

    fun onSubmitTap() {
        viewModelScope.launch {
            _isAnalyzing.value = true
            val result = mindMateApiRepository.analyzeMood(_journalBody.value)

            result.onSuccess { moodObject ->
                _isAnalyzing.value = false
                _analysisResult.value = Result.success(moodObject)
                Log.d(ConstantsManager.Success_Tag, "analyzeMood: $moodObject")

                // Save the journal entry to the database
                val newJournal = JournalItemModel(
                    title = _journalTitle.value, // Using the mood as the title
                    body = _journalBody.value,
                    date = System.currentTimeMillis(),
                    sentiment = moodObject.mood
                )
                saveJournal(newJournal)
            }
            result.onFailure { exception ->
                _isAnalyzing.value = false
                _analysisResult.value = Result.failure(exception)
                Log.e(ConstantsManager.Error_Tag, "analyzeMood: No response text", exception)
            }
        }
    }

    private fun saveJournal(journal: JournalItemModel) {
        viewModelScope.launch {
            databaseRepository.saveJournal(journal)
        }
    }

    fun onResultShown() {
        _journalTitle.value = ""
        _journalBody.value = ""
        _analysisResult.value = null
    }
}
