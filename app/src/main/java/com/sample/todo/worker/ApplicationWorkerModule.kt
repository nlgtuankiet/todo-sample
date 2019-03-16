package com.sample.todo.worker

import dagger.Module

@Module(
    includes = [
        ListenableWorkerFactoryBindingModule::class,
        WorkerFactoryBindingModule::class,
        WorkerModule::class
    ]
)
abstract class ApplicationWorkerModule