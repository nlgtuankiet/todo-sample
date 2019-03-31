package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.task.sqldelight.SelectActiveTaskMini
import com.sample.todo.data.task.sqldelight.SelectCompletedTaskMini
import com.sample.todo.data.task.sqldelight.SelectTaskMini
import com.sample.todo.data.task.sqldelight.SqlDelightTask
import com.sample.todo.data.task.sqldelight.SqlDelightTaskStatistics
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import dagger.Binds
import dagger.Module

@Module
interface MapperBindingModule {
    @Binds
    fun bindSqlDelightTaskMapper(mapper: SqlDelightTaskMapper): Mapper<SqlDelightTask, Task>
    @Binds
    fun bindTaskMapper(mapper: TaskMapper): Mapper<Task, SqlDelightTask>
    @Binds
    fun bindSelectTaskMiniMapper(mapper: SelectTaskMiniMapper): Mapper<SelectTaskMini, TaskMini>
    @Binds
    fun bindSelectActiveTaskMiniMapper(mapper: SelectActiveTaskMiniMapper): Mapper<SelectActiveTaskMini, TaskMini>
    @Binds
    fun bindSelectCompletedTaskMiniMapper(mapper: SelectCompletedTaskMiniMapper): Mapper<SelectCompletedTaskMini, TaskMini>
    @Binds
    fun bindSqlDelightTaskStatisticsMapper(mapper: SqlDelightTaskStatisticsMapper): Mapper<SqlDelightTaskStatistics, TaskStatistics>
}