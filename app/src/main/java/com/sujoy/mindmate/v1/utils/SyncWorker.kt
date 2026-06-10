package com.sujoy.mindmate.v1.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sujoy.mindmate.v1.data.models.AverageMoodDBModel
import com.sujoy.mindmate.v1.data.repositories.DatabaseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val databaseRepository: DatabaseRepository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val KEY_WORK_TYPE = "work_type"
        const val TYPE_AVERAGE_MOOD = "average_mood"
        const val KEY_TIMESTAMP = "timestamp"
        const val TAG = "SyncWorker"
    }

    override suspend fun doWork(): Result {
        val workType = inputData.getString(KEY_WORK_TYPE)

        Log.d(TAG, "doWork: Starting work of type: $workType")

        return try {
            when (workType) {
                TYPE_AVERAGE_MOOD -> calculateAverageMood()
                null -> {
                    // This handles periodic syncs that don't have a specific work type yet
                    Log.d(TAG, "doWork: Periodic sync triggered")
                    Result.success()
                }

                else -> Result.failure()
            }
        } catch (e: Exception) {
            Log.e(TAG, "doWork: Error executing work", e)
            Result.retry()
        }
    }

    private suspend fun calculateAverageMood(): Result {
        val timestamp = inputData.getLong(KEY_TIMESTAMP, System.currentTimeMillis())
        val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))

        val journals = databaseRepository.getJournalsForDate(dateStr)
        if (journals.isNotEmpty()) {
            val validScores = journals.mapNotNull { it.moodScore }
            if (validScores.isNotEmpty()) {
                val avgScore = validScores.average().toFloat()
                databaseRepository.insertAverageMood(
                    AverageMoodDBModel(date = dateStr, avgMoodScore = avgScore)
                )
            }
        }
        return Result.success()
    }
}
