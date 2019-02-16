package com.sample.todo.data

import android.content.Context
import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import dagger.BindsInstance

abstract class DataComponent {

    abstract fun provideTaskRepository(): TaskRepository

    abstract fun providePreferenceRepository(): PreferenceRepository

    abstract class Builder {
        @BindsInstance
        abstract fun seedContext(context: Context): Builder

        abstract fun build(): DataComponent
    }
}