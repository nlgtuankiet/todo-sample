package com.sample.todo.domain.model

data class Task(
    val id: String = TaskId.newInstance().value,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false
)