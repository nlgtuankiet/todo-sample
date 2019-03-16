package com.sample.todo.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseWorker(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params), CoroutineScope {
    val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun doWork(): Result = runBlocking(coroutineContext) {
        runCatching { doSuspendWork() }.fold({ Result.success() }, {
            Timber.e(it)
            Result.failure() })
    }

    abstract suspend fun doSuspendWork()

    override fun onStopped() {
        super.onStopped()
        job.cancel()
    }
}