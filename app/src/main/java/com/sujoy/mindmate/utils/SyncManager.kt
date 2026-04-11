package com.sujoy.mindmate.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun startSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "MindMateSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    fun triggerAverageMoodSync(timestamp: Long) {
        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setInputData(
                Data.Builder()
                    .putString(SyncWorker.KEY_WORK_TYPE, SyncWorker.TYPE_AVERAGE_MOOD)
                    .putLong(SyncWorker.KEY_TIMESTAMP, timestamp)
                    .build()
            )
            .build()
        workManager.enqueue(syncWorkRequest)
    }

    fun stopSync() {
        workManager.cancelUniqueWork("MindMateSyncWork")
    }
}
