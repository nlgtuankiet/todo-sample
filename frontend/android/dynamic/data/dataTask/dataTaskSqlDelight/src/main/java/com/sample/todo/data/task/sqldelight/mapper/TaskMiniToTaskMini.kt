package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.TaskMiniEntity
import com.sample.todo.main.tasks.library.domain.entity.TaskMini
import javax.inject.Inject

class TaskMiniToTaskMini @Inject constructor() : Mapper<TaskMiniEntity, TaskMini> {
    override fun invoke(from: TaskMiniEntity): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}
