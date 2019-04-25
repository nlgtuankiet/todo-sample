package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.task.room.entity.TaskMiniEntity
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

@DataScope
class TaskMiniEntityToTaskMini @Inject constructor() : Mapper<TaskMiniEntity, TaskMini> {
    override fun invoke(from: TaskMiniEntity): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.isCompleted
        )
    }
}