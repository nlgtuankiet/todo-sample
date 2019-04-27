package com.sample.todo.data.task.sqldelight.mapper

import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.sqldelight.ActiveTaskMiniEntity
import com.sample.todo.domain.model.TaskMini
import javax.inject.Inject

@DataScope
class ActiveTaskMiniToTaskMini @Inject constructor() : Mapper<ActiveTaskMiniEntity, TaskMini> {
    override fun invoke(from: ActiveTaskMiniEntity): TaskMini {
        return TaskMini(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}
