package com.sample.todo.worker

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module

@Module
abstract class WorkerFactoryBindingModule {
    @Binds
    abstract fun bindWorkerFactory(factory: TodoWorkerFactory): WorkerFactory
}