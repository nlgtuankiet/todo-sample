package com.sample.todo.data.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.room.entity.TaskStatEntity
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.TaskStat
import javax.inject.Inject

@DataScope
class TaskStatEntityMapper @Inject constructor() : Mapper<TaskStatEntity, TaskStat> {
    override fun map(from: TaskStatEntity): TaskStat {
        return TaskStat(
            taskCount = from.taskCount,
            completedTaskCount = from.completedTaskCount,
            activeTaskCount = from.activeTaskCount
        )
    }
}