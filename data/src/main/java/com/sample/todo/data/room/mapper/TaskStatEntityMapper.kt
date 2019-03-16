package com.sample.todo.data.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.room.entity.TaskStatisticsEntity
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.TaskStatistics
import javax.inject.Inject

@DataScope
class TaskStatEntityMapper @Inject constructor() : Mapper<TaskStatisticsEntity, TaskStatistics> {
    override fun map(from: TaskStatisticsEntity): TaskStatistics {
        return TaskStatistics(
            taskCount = from.taskCount,
            completedTaskCount = from.completedTaskCount,
            activeTaskCount = from.activeTaskCount
        )
    }
}