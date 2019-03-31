package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.task.sqldelight.SelectActiveTaskMini
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

class SelectActiveTaskMiniMapper @Inject constructor() : Mapper<SelectActiveTaskMini, TaskMini> {
    override fun map(from: SelectActiveTaskMini): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}