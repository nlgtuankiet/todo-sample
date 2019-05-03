package com.sample.todo.domain

import com.sample.todo.domain.entity.Task
import com.sample.todo.domain.entity.TaskId


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
