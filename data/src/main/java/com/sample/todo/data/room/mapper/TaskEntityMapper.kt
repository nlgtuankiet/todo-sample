package com.sample.todo.data.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.domain.model.Task
import org.threeten.bp.Instant
import javax.inject.Inject

class TaskEntityMapper @Inject constructor() : Mapper<TaskEntity, Task> {
    override fun map(from: TaskEntity): Task {
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