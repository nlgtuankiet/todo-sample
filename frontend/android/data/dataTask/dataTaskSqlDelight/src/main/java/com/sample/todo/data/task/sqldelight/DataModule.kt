package com.sample.todo.data.task.sqldelight

import com.sample.todo.data.preference.PreferenceModule
import com.sample.todo.data.task.sqldelight.mapper.MapperBindingModule
import dagger.Module

@Module(
    includes = [
        TodoDatabaseModule::class,
        PreferenceModule::class,
        MapperBindingModule::class
    ]
)
interface DataModule