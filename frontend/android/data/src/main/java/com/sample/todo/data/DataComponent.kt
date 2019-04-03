package com.sample.todo.data

import android.content.Context
import androidx.work.WorkManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import dagger.BindsInstance

interface DataComponent {

    fun provideTaskRepository(): TaskRepository

    fun providePreferenceRepository(): PreferenceRepository

    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }
}