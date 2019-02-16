package com.sample.todo.data.sqldelight

import com.sample.todo.data.preference.PreferenceModule
import com.sample.todo.data.sqldelight.mapper.MapperBindingModule
import dagger.Module

@Module(
    includes = [
        TodoDatabaseModule::class,
        PreferenceModule::class,
        MapperBindingModule::class
    ]
)
interface DataModule