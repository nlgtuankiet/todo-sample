package com.sample.todo.data.task.room.mapper

import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.room.entity.TaskEntity
import com.sample.todo.domain.model.Task
import org.threeten.bp.Instant
import javax.inject.Inject

class TaskEntityToTask @Inject constructor() : Mapper<TaskEntity, Task> {
    override fun invoke(from: TaskEntity): Task {
        return Task(
            id = from.id,
            title = from.title,
            description = from.description,
            isCompleted = from.isCompleted,
            createTime = Instant.ofEpochSecond(from.createTime),
            updateTime = Instant.ofEpochSecond(from.updateTime)
        )
    }
}
