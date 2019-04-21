package com.sample.todo.work.downloadmodule

import dagger.Module

@Module(
    includes = [
        DownloadModuleWorkerFactoryBindingModule::class
    ]
)
abstract class DownloadModuleWorkerModule
