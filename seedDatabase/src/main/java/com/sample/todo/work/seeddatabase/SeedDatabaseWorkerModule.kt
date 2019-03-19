package com.sample.todo.work.seeddatabase

import com.sample.todo.work.seeddatabase.service.ServiceBindingModule
import dagger.Module

@Module(
    includes = [
        SeedDatabaseWorkerFactoryBindingModule::class,
        ServiceBindingModule::class
    ]
)
abstract class SeedDatabaseWorkerModule