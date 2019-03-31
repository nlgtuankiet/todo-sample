package com.sample.todo.di

import com.sample.todo.work.WorkManagerModule
import com.sample.todo.work.WorkerFactoryBindingModule
import com.sample.todo.work.downloadmodule.DownloadModuleWorkerModule
import com.sample.todo.work.seeddatabase.SeedDatabaseWorkerModule
import dagger.Module

@Module(
    includes = [
        WorkManagerModule::class,
        WorkerFactoryBindingModule::class,
        SeedDatabaseWorkerModule::class,
        DownloadModuleWorkerModule::class
    ]
)
interface WorkModule