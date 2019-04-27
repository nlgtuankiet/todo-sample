package com.sample.todo.data

import android.content.Context
import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import dagger.BindsInstance

interface DataComponent {

    fun provideTaskRepository(): TaskRepository

    fun providePreferenceRepository(): PreferenceRepository

    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    abstract class Companion(val factory: Factory) {
        operator fun invoke(context: Context): DataComponent {
            return factory.create(context)
        }
    }
}
