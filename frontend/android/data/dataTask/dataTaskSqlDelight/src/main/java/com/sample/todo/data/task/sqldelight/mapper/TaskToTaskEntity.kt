package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.TaskEntity
import com.sample.todo.data.task.sqldelight.TaskEntityImp
import com.sample.todo.domain.model.Task
import javax.inject.Inject

class TaskToTaskEntity @Inject constructor() : Mapper<Task, TaskEntity> {
    override fun invoke(from: Task): TaskEntity {
        return TaskEntityImp(
            id = from.id,
            title = from.title,
            description = from.description,
            completed = if (from.isCompleted) 1 else 0
        )
    }
}