package com.sample.todo.work

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module

@Module
abstract class WorkerFactoryBindingModule {
    @Binds
    abstract fun bindWorkerFactory(factory: TodoWorkerFactory): WorkerFactory
}