package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.SelectActiveTaskMini
import com.sample.todo.data.task.sqldelight.SelectCompletedTaskMini
import com.sample.todo.data.task.sqldelight.SelectTaskMini
import com.sample.todo.data.task.sqldelight.TaskEntity
import com.sample.todo.data.task.sqldelight.TaskStatisticsEntity
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import dagger.Binds
import dagger.Module

@Module
interface MapperBindingModule {
    @Binds
    fun bindSqlDelightTaskMapper(mapper: TaskEntityToTask): Mapper<TaskEntity, Task>

    @Binds
    fun bindTaskMapper(mapper: TaskToTaskEntity): Mapper<Task, TaskEntity>

    @Binds
    fun bindSelectTaskMiniMapper(mapper: TaskMiniToTaskMini): Mapper<SelectTaskMini, TaskMini>

    @Binds
    fun bindSelectActiveTaskMiniMapper(mapper: ActiveTaskMiniToTaskMini): Mapper<SelectActiveTaskMini, TaskMini>

    @Binds
    fun bindSelectCompletedTaskMiniMapper(mapper: CompletedTaskMiniToTaskMini): Mapper<SelectCompletedTaskMini, TaskMini>

    @Binds
    fun bindSqlDelightTaskStatisticsMapper(mapper: TaskStatisticsEntityToTaskStatistics): Mapper<TaskStatisticsEntity, TaskStatistics>
}