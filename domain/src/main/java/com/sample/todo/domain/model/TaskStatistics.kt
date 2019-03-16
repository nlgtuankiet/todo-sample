package com.sample.todo.domain.model

data class TaskStatistics(
    val taskCount: Long,
    val completedTaskCount: Long,
    val activeTaskCount: Long
)