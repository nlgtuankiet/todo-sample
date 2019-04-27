package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.TaskEntity
import com.sample.todo.domain.model.Task
import javax.inject.Inject

class TaskEntityToTask @Inject constructor() : Mapper<TaskEntity, Task> {
    override fun invoke(from: TaskEntity): Task {
        return Task(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}
