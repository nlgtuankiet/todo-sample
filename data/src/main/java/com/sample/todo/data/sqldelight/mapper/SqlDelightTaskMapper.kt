package com.sample.todo.data.sqldelight.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.sqldelight.SqlDelightTask
import com.sample.todo.domain.model.Task
import javax.inject.Inject

class SqlDelightTaskMapper @Inject constructor() : Mapper<SqlDelightTask, Task> {
    override fun map(from: SqlDelightTask): Task {
        return Task(
            id = from.id,
            title = from.title,
            isCompleted = from.completed == 1L
        )
    }
}