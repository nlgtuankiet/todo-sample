package com.sample.todo.data.task.room

import com.sample.todo.data.task.room.mapper.MapperBindingModule
import com.sample.todo.data.preference.PreferenceModule
import dagger.Module

@Module(
    includes = [
        TodoDatabaseModule::class,
        PreferenceModule::class,
        MapperBindingModule::class
    ]
)
interface DataModule
