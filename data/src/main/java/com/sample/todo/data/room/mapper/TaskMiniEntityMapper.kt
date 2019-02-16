package com.sample.todo.data.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.room.entity.TaskMiniEntity
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

@DataScope
class TaskMiniEntityMapper @Inject constructor() : Mapper<TaskMiniEntity, TaskMini> {
    override fun map(from: TaskMiniEntity): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.isCompleted
        )
    }
}