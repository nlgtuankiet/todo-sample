package com.sample.todo.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber
import androidx.work.ListenableWorker.Result as WorkResult

class HelloWorldWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): WorkResult {
        Timber.d("Hello world!")

        return WorkResult.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}
