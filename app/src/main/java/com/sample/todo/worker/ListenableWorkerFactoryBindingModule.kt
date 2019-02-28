package com.sample.todo.worker

import com.sample.todo.di.WorkerKey
import com.sample.todo.worker.downloadmodule.DownloadModuleWorker
import com.sample.todo.worker.seeddatabase.SeedDatabaseWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListenableWorkerFactoryBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker::class)
    abstract fun bindSeedDatabaseWorker(factory: SeedDatabaseWorker.Factory): ListenableWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(DownloadModuleWorker::class)
    abstract fun bindDownloadModuleWorker(factory: DownloadModuleWorker.Factory): ListenableWorkerFactory
}
