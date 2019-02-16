package com.sample.todo.data.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.sqldelight.SqlDelightTaskStatistics
import com.sample.todo.domain.model.TaskStat
import javax.inject.Inject

class SqlDelightTaskStatisticsMapper @Inject constructor() : Mapper<SqlDelightTaskStatistics, TaskStat> {
    override fun map(from: SqlDelightTaskStatistics): TaskStat {
        return TaskStat(
            taskCount = from.taskCount,
            completedTaskCount = from.completedTaskCount,
            activeTaskCount = from.activeTaskCount
        )
    }
}