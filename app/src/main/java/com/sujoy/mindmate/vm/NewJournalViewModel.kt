package com.sujoy.mindmate.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.mindmate.models.AnalyzedMoodObject
import com.sujoy.mindmate.repositories.MindMateAppRepoImpl
import com.sujoy.mindmate.repositories.MindMateAppRepository
import com.sujoy.mindmate.utils.ConstantsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewJournalViewModel : ViewModel() {
    private val mindMateRepository: MindMateAppRepository = MindMateAppRepoImpl()

    private val _journalBody = MutableStateFlow("")
    val journalBody: StateFlow<String> = _journalBody.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analysisResult = MutableStateFlow<Result<AnalyzedMoodObject>?>(null)
    val analysisResult: StateFlow<Result<AnalyzedMoodObject>?> = _analysisResult.asStateFlow()


    fun updateJournalBody(newBody: String) {
        _journalBody.value = newBody
    }

    fun onSubmitTap() {
        viewModelScope.launch {
            _isAnalyzing.value = true
            val result = mindMateRepository.analyzeMood(_journalBody.value)

            result.onSuccess {
                _isAnalyzing.value = false
                _analysisResult.value = result
                Log.d(ConstantsManager.Success_Tag, "analyzeMood: $result")
            }
            result.onFailure {
                _isAnalyzing.value = false
                Log.e(ConstantsManager.Error_Tag, "analyzeMood: No response text")
            }
        }
    }

    fun onSnackbarShown() {
        _journalBody.value = ""
        _analysisResult.value = null
    }
}