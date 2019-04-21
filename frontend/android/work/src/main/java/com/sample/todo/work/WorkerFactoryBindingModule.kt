package com.sample.todo.work

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module

@Module
interface WorkerFactoryBindingModule {
    @Binds
    fun bindWorkerFactory(factory: TodoWorkerFactory): WorkerFactory
}