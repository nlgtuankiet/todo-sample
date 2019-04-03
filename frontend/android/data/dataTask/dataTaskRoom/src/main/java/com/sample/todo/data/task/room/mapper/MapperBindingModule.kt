package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.task.room.entity.SearchResultEntity
import com.sample.todo.data.task.room.entity.SearchResultStatisticsEntity
import com.sample.todo.data.task.room.entity.TaskEntity
import com.sample.todo.data.task.room.entity.TaskMiniEntity
import com.sample.todo.data.task.room.entity.TaskStatisticsEntity
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import dagger.Binds
import dagger.Module

@Module
interface MapperBindingModule {

    @Binds
    fun bindTaskMapper(mapper: TaskMapper): Mapper<Task, TaskEntity>

    @Binds
    fun bindTaskMiniMapper(mapper: TaskMiniEntityMapper): Mapper<TaskMiniEntity, TaskMini>

    @Binds
    fun bindTaskStatMapper(mapper: TaskStatEntityMapper): Mapper<TaskStatisticsEntity, TaskStatistics>

    @Binds
    fun bindSearchResultEntityMapper(mapper: SearchResultEntityMapper): Mapper<SearchResultEntity, SearchResult>

    @Binds
    fun bindTaskEntityMapper(mapper: TaskEntityMapper): Mapper<TaskEntity, Task>

    @Binds
    fun bindSearchResultStatisticsEntityMapper(mapper: SearchResultStatisticsEntityMapper): Mapper<SearchResultStatisticsEntity, SearchResultStatistics>
}