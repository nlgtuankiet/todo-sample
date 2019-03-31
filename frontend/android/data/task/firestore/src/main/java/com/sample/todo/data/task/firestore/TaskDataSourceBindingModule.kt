package com.sample.todo.data.task.firestore

import com.sample.todo.data.TaskDataSource
import dagger.Binds
import dagger.Module

@Module
interface TaskDataSourceBindingModule {
    @Binds
    fun bindTaskMapper(datasource: TaskDataSourceImpl): TaskDataSource
}