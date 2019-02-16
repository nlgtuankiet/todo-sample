package com.sample.todo.domain

import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskId

object TestData {

    val tasks = listOf(
        Task(

            title = "title 1",
            description = "des 1",
            isCompleted = true
        ),
        Task(
            title = "title 2",
            description = "des 2",
            isCompleted = false
        )
    )
    val invalidTaskId = TaskId("???")
    val validTaskId = TaskId.newInstance()
}
