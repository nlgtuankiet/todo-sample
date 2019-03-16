package com.sample.todo.worker

import androidx.work.WorkManager
import com.sample.todo.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
abstract class WorkerModule {
    @Module
    companion object {
        @Provides
        @ApplicationScope
        @JvmStatic
        fun provideWorkManager(): WorkManager {
            return WorkManager.getInstance()
        }
    }
}