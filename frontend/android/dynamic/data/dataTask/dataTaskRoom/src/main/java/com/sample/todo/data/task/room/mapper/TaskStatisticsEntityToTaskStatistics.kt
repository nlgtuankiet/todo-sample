package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.task.room.entity.TaskStatisticsEntity
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.main.statistics.domain.entity.TaskStatistics
import javax.inject.Inject

@DataScope
class TaskStatisticsEntityToTaskStatistics @Inject constructor() : Mapper<TaskStatisticsEntity, TaskStatistics> {
    override fun invoke(from: TaskStatisticsEntity): TaskStatistics {
        return TaskStatistics(
            taskCount = from.taskCount,
            completedTaskCount = from.completedTaskCount,
            activeTaskCount = from.activeTaskCount
        )
    }
}
