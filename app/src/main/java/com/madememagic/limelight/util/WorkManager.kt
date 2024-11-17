package com.madememagic.limelight.util

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.madememagic.limelight.domain.repository.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class AppWorkerManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notesRepository: MoviesRepository,
    private val notificationManager: NotificationManager
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {

            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }

    companion object {
        private const val WORK_NAME = "app_worker"

        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<AppWorkerManager>(
                1, TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }

        fun scheduleImmediate(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<AppWorkerManager>(
                1, TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
