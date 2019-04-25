package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.CompletedTaskMiniEntity
import com.sample.todo.data.task.sqldelight.SelectCompletedTaskMini
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

@DataScope
class CompletedTaskMiniToTaskMini @Inject constructor() : Mapper<CompletedTaskMiniEntity, TaskMini> {
    override fun invoke(from: CompletedTaskMiniEntity): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}