package com.sample.todo.data.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.sqldelight.SelectTaskMini
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

class SelectTaskMiniMapper @Inject constructor() : Mapper<SelectTaskMini, TaskMini> {
    override fun map(from: SelectTaskMini): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}