package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.TaskStatisticsEntity
import com.sample.todo.domain.model.TaskStatistics
import javax.inject.Inject

class TaskStatisticsEntityToTaskStatistics @Inject constructor() : Mapper<TaskStatisticsEntity, TaskStatistics> {
    override fun invoke(from: TaskStatisticsEntity): TaskStatistics {
        return TaskStatistics(
            taskCount = from.taskCount,
            completedTaskCount = from.completedTaskCount,
            activeTaskCount = from.activeTaskCount
        )
    }
}
