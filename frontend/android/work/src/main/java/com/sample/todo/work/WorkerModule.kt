package com.sample.todo.work

import dagger.Module

@Module(
    includes = [
        WorkerFactoryBindingModule::class
    ]
)
abstract class WorkerModule