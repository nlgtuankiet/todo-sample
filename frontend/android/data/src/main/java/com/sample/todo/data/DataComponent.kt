package com.sample.todo.data

import android.content.Context
import com.sample.todo.domain.repository.PreferenceRepository
import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.downloadmodule.domain.gateway.SplitInstallGateway
import com.sample.todo.main.about.library.repository.AddEditRepository
import com.sample.todo.main.search.library.domain.repository.SearchRepository
import com.sample.todo.main.statistics.domain.repository.StatisticsRepository
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import dagger.Binds
import dagger.BindsInstance

interface DataComponent {

    fun TasksRepository(): TasksRepository
    fun TaskRepository(): TaskRepository
    fun TaskDetailRepository(): TaskDetailRepository
    fun AddEditRepository(): AddEditRepository
    fun SearchRepository(): SearchRepository
    fun StatisticsRepository(): StatisticsRepository
    fun SplitInstallGateway(): SplitInstallGateway

    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    abstract class Companion(val factory: Factory) {
        operator fun invoke(context: Context): DataComponent {
            return factory.create(context)
        }
    }
}
