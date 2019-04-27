package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.core.Mapper
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
    fun bindTaskMapper(mapper: TaskToTaskEntity): Mapper<Task, TaskEntity>

    @Binds
    fun bindTaskMiniMapper(mapper: TaskMiniEntityToTaskMini): Mapper<TaskMiniEntity, TaskMini>

    @Binds
    fun bindTaskStatMapper(mapper: TaskStatisticsEntityToTaskStatistics): Mapper<TaskStatisticsEntity, TaskStatistics>

    @Binds
    fun bindSearchResultEntityMapper(mapper: SearchResultEntityToSearchResult): Mapper<SearchResultEntity, SearchResult>

    @Binds
    fun bindTaskEntityMapper(mapper: TaskEntityToTask): Mapper<TaskEntity, Task>

    @Binds
    fun bindSearchResultStatisticsEntityMapper(mapper: SearchResultStatisticsEntityToSearchResultStatistics): Mapper<SearchResultStatisticsEntity, SearchResultStatistics>
}
