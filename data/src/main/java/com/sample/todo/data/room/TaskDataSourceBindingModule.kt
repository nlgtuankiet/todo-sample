package com.sample.todo.data.room

import com.sample.todo.data.TaskDataSource
import dagger.Binds
import dagger.Module

@Module
interface TaskDataSourceBindingModule {
    @Binds
    fun bind(taskDataSource: TaskDataSourceImpl): TaskDataSource
}
