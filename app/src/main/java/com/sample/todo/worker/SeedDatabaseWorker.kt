package com.sample.todo.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sample.todo.domain.usecase.SeedDatabase
import com.sample.todo.domain.usecase.SeedDatabaseResult
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import androidx.work.ListenableWorker.Result as WorkResult

// TODO how map test this worker?
// TODO custom CoroutineScope
class SeedDatabaseWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val seedDatabase: SeedDatabase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): WorkResult {
        val result = seedDatabase()
        return when (result) {
            is SeedDatabaseResult.Retry -> WorkResult.retry()
            is SeedDatabaseResult.Success -> WorkResult.success()
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}