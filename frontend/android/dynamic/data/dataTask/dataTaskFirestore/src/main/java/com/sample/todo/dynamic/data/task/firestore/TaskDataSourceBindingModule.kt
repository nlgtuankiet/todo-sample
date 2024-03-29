package com.sample.todo.dynamic.data.task.firestore

import com.sample.todo.data.TaskDataSource
import dagger.Binds
import dagger.Module

@Module
interface TaskDataSourceBindingModule {
    @Binds
    fun bindTaskMapper(datasource: TaskDataSourceImpl): TaskDataSource
}
