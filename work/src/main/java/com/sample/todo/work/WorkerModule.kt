package com.sample.todo.work

import dagger.Module

@Module(
    includes = [
        WorkerFactoryBindingModule::class,
        WorkManagerModule::class
    ]
)
abstract class WorkerModule