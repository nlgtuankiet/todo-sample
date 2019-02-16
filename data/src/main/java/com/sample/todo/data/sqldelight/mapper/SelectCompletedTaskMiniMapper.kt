package com.sample.todo.data.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.sqldelight.SelectCompletedTaskMini
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

class SelectCompletedTaskMiniMapper @Inject constructor() : Mapper<SelectCompletedTaskMini, TaskMini> {
    override fun map(from: SelectCompletedTaskMini): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}