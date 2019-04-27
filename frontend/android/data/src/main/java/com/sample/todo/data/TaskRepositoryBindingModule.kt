package com.sample.todo.data

import com.sample.todo.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module

@Module
interface TaskRepositoryBindingModule {
    @Binds
    fun bind(taskRepository: TaskRepositoryImpl): TaskRepository
}
