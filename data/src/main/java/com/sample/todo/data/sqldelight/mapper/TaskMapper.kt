package com.sample.todo.data.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.sqldelight.SqlDelightTask
import com.sample.todo.data.sqldelight.SqlDelightTaskImp
import com.sample.todo.domain.model.Task
import javax.inject.Inject

class TaskMapper @Inject constructor() : Mapper<Task, SqlDelightTask> {
    override fun map(from: Task): SqlDelightTask {
        return SqlDelightTaskImp(
            id = from.id,
            title = from.title,
            description = from.description,
            completed = if (from.isCompleted) 1 else 0
        )
    }
}