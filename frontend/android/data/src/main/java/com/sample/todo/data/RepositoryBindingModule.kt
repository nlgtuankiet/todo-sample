package com.sample.todo.data

import com.sample.todo.domain.repository.TaskRepository
import com.sample.todo.downloadmodule.domain.gateway.SplitInstallGateway
import com.sample.todo.main.about.library.repository.AddEditRepository
import com.sample.todo.main.search.library.domain.repository.SearchRepository
import com.sample.todo.main.statistics.domain.repository.StatisticsRepository
import com.sample.todo.main.taskdetail.library.domain.repository.TaskDetailRepository
import com.sample.todo.main.tasks.library.domain.repository.TasksRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryBindingModule {
    @Binds
    fun TaskRepository(impl: TaskRepositoryImpl): TaskRepository
    @Binds
    fun TasksRepository(impl: TaskRepositoryImpl): TasksRepository

    @Binds
    fun TaskDetailRepository(impl: TaskRepositoryImpl): TaskDetailRepository

    @Binds
    fun AddEditRepository(impl: TaskRepositoryImpl): AddEditRepository

    @Binds
    fun SearchRepository(impl: TaskRepositoryImpl): SearchRepository

    @Binds
    fun StatisticsRepository(impl: TaskRepositoryImpl): StatisticsRepository

    @Binds
    fun SplitInstallGateway(impl: SplitInstallGatewayImpl): SplitInstallGateway
}
