package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.task.sqldelight.SqlDelightTaskStatistics
import com.sample.todo.domain.model.TaskStatistics
import javax.inject.Inject

class SqlDelightTaskStatisticsMapper @Inject constructor() : Mapper<SqlDelightTaskStatistics, TaskStatistics> {
    override fun map(from: SqlDelightTaskStatistics): TaskStatistics {
        return TaskStatistics(
            taskCount = from.taskCount,
            completedTaskCount = from.completedTaskCount,
            activeTaskCount = from.activeTaskCount
        )
    }
}