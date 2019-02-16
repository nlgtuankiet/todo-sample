package com.sample.todo.worker

import com.sample.todo.di.WorkerKey
import com.sample.todo.worker.seeddatabase.SeedDatabase2Worker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(HelloWorldWorker::class)
    abstract fun bindHelloWorldWorker(factory: HelloWorldWorker.Factory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker::class)
    abstract fun bindSeedDatabaseWorker(factory: SeedDatabaseWorker.Factory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(SeedDatabase2Worker::class)
    abstract fun bindSeedDatabase2Worker(factory: SeedDatabase2Worker.Factory): ChildWorkerFactory
}
