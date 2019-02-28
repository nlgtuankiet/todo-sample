package com.sample.todo.initializer

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.facebook.stetho.Stetho
import com.sample.todo.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class WorkManagerInitializer @Inject constructor(
    private val workerFactory: WorkerFactory
) : Initializer {
    override fun initialize(application: Application) {
        WorkManager.initialize(
            application,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )
    }
}